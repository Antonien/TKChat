package fr.talyoki.tkchat.manager;

import fr.talyoki.tkchat.data.InfoPlayer;
import fr.talyoki.tkchat.data.message.MsgScope;
import fr.talyoki.tkchat.data.TextInLive;
import fr.talyoki.tkchat.data.message.PrivateMessageBuilder;
import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
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
	public void sendMessage(ProxiedPlayer player, String msg, MsgScope scope)
	{
		// Récupération des info du joueur
		InfoPlayer infoPlayer = new InfoPlayer(luckPerms, configManager, player);

		// Récupération du serveur d'origine du message
		String server = player.getServer().getInfo().getName();

		// Création de l'icon pour situer le joueur entre les serveurs
		String aliasGlobal = configManager.listAliasGlobalPrefix.getOrDefault(server, configManager.listAliasGlobalPrefix.get("default")).replace('&', '§');
		String aliasServer = configManager.listAliasServerPrefix.getOrDefault(server, configManager.listAliasServerPrefix.get("default")).replace('&', '§');

		// Création du prefix live clickable
		TextInLive inLive = new TextInLive(configManager, streamerManager, player);

		// Envoi du message
		switch(scope)
		{
			case GLOBAL:
				// Création du message complet
				ComponentBuilder textGlobal = new ComponentBuilder();
				textGlobal.append(aliasGlobal);
				textGlobal.append(inLive.getPrefix());
				textGlobal.append(infoPlayer.getPrefixGroup());
				//textGlobal.append(infoPlayer.getPrefixPlayer());
				textGlobal.append(player.getDisplayName() + ChatColor.WHITE + " : " + msg);

				sendMessageGlobal(textGlobal);
				break;
			case SERVER:
				// Création du message complet
				ComponentBuilder textServer = new ComponentBuilder();
				textServer.append(aliasServer);
				textServer.append(inLive.getPrefix());
				textServer.append(infoPlayer.getPrefixGroup());
				textServer.append(infoPlayer.getPrefixPlayer());
				textServer.append(player.getDisplayName() + ChatColor.WHITE + " : " + msg);

				sendMessageServer(textServer, player.getServer());

				// Envoi d'une copie du message aux modo
				Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();
				for(ProxiedPlayer proxiedPlayer : allPlayers)
				{
					// Si le modo n'est pas déjà dans le serveur
					if(moderatorsGlobalView.isActive(proxiedPlayer) && !proxiedPlayer.getServer().getInfo().getName().equals(player.getServer().getInfo().getName()))
					{
						proxiedPlayer.sendMessage(textServer.create());
					}
				}
		}
	}

	// Envoi du message global
	public void sendMessageGlobal(ComponentBuilder msgFull)
	{
		ProxyServer.getInstance().broadcast(msgFull.create());
	}

	// Envoi du message sur un serveur
	public void sendMessageServer(ComponentBuilder msgFull, Server server)
	{
		server.getInfo().getPlayers().forEach((player) -> {
			player.sendMessage(msgFull.create());
		});

		ProxyServer.getInstance().getLogger().log(Level.INFO, "[" + server.getInfo().getName() + "] <-> " + msgFull);
	}

	// Envoie d'un message privé
	public void sendMessagePlayer(ProxiedPlayer playerDest, ProxiedPlayer playerExpe, String msgFull)
	{
		// Génération de l'affichage d'un message privé
		PrivateMessageBuilder privateMessage = new PrivateMessageBuilder(playerExpe.getDisplayName(), playerDest.getDisplayName(), msgFull);

		playerDest.sendMessage(privateMessage.getMessageDest().create());
		playerExpe.sendMessage(privateMessage.getMessageExpe().create());

		// Envoie du message à la modération
		Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();
		TextComponent msgPrivate = new TextComponent(ChatColor.GRAY + playerExpe.getName() + " -> " + playerDest.getName() + " : ");
		msgPrivate.addExtra(msgFull);
		for(ProxiedPlayer player : allPlayers)
		{
			// Si le modo n'est pas déjà dans le serveur
			if(moderatorsPrivateView.isActive(player) && !player.getName().equals(playerDest.getName()) && !player.getName().equals(playerExpe.getName()))
			{
				player.sendMessage(msgPrivate);
			}
		}
		// Envoi dans les logs
		ProxyServer.getInstance().getLogger().log(Level.INFO, "[private] <-> " + msgPrivate.toPlainText());
	}
}
