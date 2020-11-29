package fr.talyoki.tkchat.manager;

import fr.talyoki.tkchat.data.MsgScope;
import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import java.util.Collection;
import java.util.logging.Level;

public class MessageManager
{
	private LuckPermInfo luckPerms;
	private StreamerManager streamerManager;
	private ModeratorsGlobalViewManager moderatorsGlobalView;
	private ModeratorsPrivateViewManager moderatorsPrivateView;
	private ConfigManager configManager;

	public MessageManager(Manager manager)
	{
		this.luckPerms = manager.luckPerms;
		this.streamerManager = manager.streamerManager;
		this.moderatorsGlobalView = manager.moderatorsGlobalViewManager;
		this.moderatorsPrivateView = manager.moderatorsPrivateViewManager;
		this.configManager = manager.configManager;
	}

	// Modèle de message standard
	public void SendMessage(ProxiedPlayer player, String msg, MsgScope scope)
	{
		// Déclaration du joueur version API Luckperms
		User user = luckPerms.setUserLpByUUID(player.getUniqueId());

		// Récupération du serveur d'origine du message
		String server = player.getServer().getInfo().getName();

		// Création de l'icon pour situer le joueur entre les serveurs
		String aliasGlobal = configManager.listAliasGlobalPrefix.getOrDefault(server, configManager.listAliasGlobalPrefix.get("default"));
		String aliasServer = configManager.listAliasServerPrefix.getOrDefault(server, configManager.listAliasServerPrefix.get("default"));

		// Récupération du prefix du joueur
		String prefixGroup = this.luckPerms.getPrefixGroup(user);
		String prefixUser = this.luckPerms.getPrefixUser(user);

		// Création du prefix live clickable
		TextComponent prefixLive = new TextComponent("");
		if(this.streamerManager.isInLive(player))
		{
			String psTwitch = this.streamerManager.getPseudoTwitch(player.getName());
			prefixLive = new TextComponent(configManager.listPrefix.getOrDefault("live", "").replace('&', '§'));
			prefixLive.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://www.twitch.tv/" + psTwitch));
			prefixLive.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new Content[] { new Text("Regarder le stream") }));
		}

		// Envoi du message
		switch(scope)
		{
			case GLOBAL:
				// Création du message complet
				TextComponent textGlobal = new TextComponent();
				textGlobal.addExtra(aliasGlobal.replace('&', '§'));
				textGlobal.addExtra(prefixLive);
				textGlobal.addExtra(ChatColor.translateAlternateColorCodes('&', prefixGroup) + ChatColor.translateAlternateColorCodes('&', prefixUser) + player.getDisplayName() + ChatColor.WHITE + " : " + msg);

				SendMessageGlobal(textGlobal);
				break;
			case SERVER:
				// Création du message complet
				TextComponent textServer = new TextComponent();
				textServer.addExtra(aliasServer.replace('&', '§'));
				textServer.addExtra(prefixLive);
				textServer.addExtra(ChatColor.translateAlternateColorCodes('&', prefixGroup) + ChatColor.translateAlternateColorCodes('&', prefixUser) + player.getDisplayName() + ChatColor.WHITE + " : " + msg);

				SendMessageServer(textServer, player.getServer());
				// Envoi d'une copie du message aux modo
				Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();
				for(ProxiedPlayer allPlayer : allPlayers)
				{
					// Si le modo n'est pas déjà dans le serveur
					if(moderatorsGlobalView.isActive(allPlayer)
							&& !allPlayer.getServer().getInfo().getName().equals(player.getServer().getInfo().getName()))
					{
						allPlayer.sendMessage(textServer);
					}
				}
		}
	}

	// Envoi du message global
	public void SendMessageGlobal(TextComponent msgFull)
	{
		ProxyServer.getInstance().broadcast(msgFull);
	}

	// Envoi du message sur un serveur
	public void SendMessageServer(TextComponent msgFull, Server server)
	{
		server.getInfo().getPlayers().forEach((player) -> {
			SendMessagePlayer(player, null, msgFull, false);
		});
		ProxyServer.getInstance().getLogger().log(Level.INFO, "[" + server.getInfo().getName() + "] <-> " + msgFull.toPlainText());
	}

	// Envoie d'un message privé
	public void SendMessagePlayer(ProxiedPlayer playerDest, ProxiedPlayer playerExpe, TextComponent msgFull, Boolean isPrivate)
	{
		if(isPrivate)
		{
			// Génération de l'affichage d'un message privé
			TextComponent privateMsgDest = new TextComponent(
					ChatColor.GOLD + playerExpe.getDisplayName() + ChatColor.DARK_GRAY + " -> " + ChatColor.GOLD + "Toi" + ChatColor.DARK_GRAY + " : "
							+ ChatColor.WHITE);
			privateMsgDest.addExtra(msgFull);

			TextComponent privateMsgExpe = new TextComponent(
					ChatColor.GOLD + "Toi" + ChatColor.DARK_GRAY + " -> " + ChatColor.GOLD + playerDest.getDisplayName() + ChatColor.DARK_GRAY + " : "
							+ ChatColor.WHITE);
			privateMsgExpe.addExtra(msgFull);

			playerDest.sendMessage(privateMsgDest);
			playerExpe.sendMessage(privateMsgExpe);

			// Envoie du message à la modération
			Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();
			TextComponent msgPrivate = new TextComponent(ChatColor.GRAY + playerExpe.getName() + " -> " + playerDest.getName() + " : ");
			msgPrivate.addExtra(msgFull);
			for(ProxiedPlayer player : allPlayers)
			{
				// Si le modo n'est pas déjà dans le serveur
				if(moderatorsPrivateView.isActive(player) && !player.getName().equals(playerDest.getName())
						&& !player.getName().equals(playerExpe.getName()))
				{
					player.sendMessage(msgPrivate);
				}
			}
			// Envoi dans les logs
			ProxyServer.getInstance().getLogger().log(Level.INFO, "[private] <-> " + msgPrivate.toPlainText());
		}
		else
		{
			playerDest.sendMessage(msgFull);
		}
	}
}
