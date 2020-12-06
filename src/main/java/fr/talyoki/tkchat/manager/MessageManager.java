package fr.talyoki.tkchat.manager;

import fr.talyoki.tkchat.data.Permissions;
import fr.talyoki.tkchat.data.message.*;
import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import fr.talyoki.tkchat.utils.StringUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
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
		// Récupération des infos du joueur
		PrefixGroup prefixGroup = new PrefixGroup(luckPerms, configManager, player);
		PrefixUser prefixUser = new PrefixUser(luckPerms, configManager, player);

		// Récupération du serveur d'origine du message
		String server = player.getServer().getInfo().getName();

		// Création de l'icon pour situer le joueur entre les serveurs
		String aliasGlobal = StringUtil.HEXtoText(configManager.listAliasGlobalPrefix.getOrDefault(server, configManager.listAliasGlobalPrefix.get("default")));
		String aliasServer = StringUtil.HEXtoText(configManager.listAliasServerPrefix.getOrDefault(server, configManager.listAliasServerPrefix.get("default")));

		// Création du prefix live clickable
		TextInLive inLive = new TextInLive(configManager, streamerManager, player);

		// Envoi du message
		switch(scope)
		{
			case GLOBAL:
				// Génération des logs
				StringBuilder logMessageGlobal = new StringBuilder();
				logMessageGlobal.append("[GLOBAL] ");
				logMessageGlobal.append(StringUtil.removeAllColor(aliasGlobal));
				logMessageGlobal.append(inLive.getInLiveStr());
				logMessageGlobal.append(prefixGroup.getPrefixGroupStr());
				logMessageGlobal.append(prefixUser.getPrefixUserStr());
				logMessageGlobal.append(player.getDisplayName()).append(" : ");
				logMessageGlobal.append(StringUtil.removeAllColor(msg));

				// Création du message complet
				ComponentBuilder textGlobal = new ComponentBuilder();
				textGlobal.appendLegacy(aliasGlobal);
				textGlobal.append(inLive.getPrefix().create(), ComponentBuilder.FormatRetention.FORMATTING);
				textGlobal.append(prefixGroup.getPrefixGroup().create(), ComponentBuilder.FormatRetention.FORMATTING);
				textGlobal.append(prefixUser.getPrefixPlayer().create(), ComponentBuilder.FormatRetention.FORMATTING);
				textGlobal.appendLegacy(player.getDisplayName() + ChatColor.WHITE + " : ");
				textGlobal.append(TextComponent.fromLegacyText(msg));

				sendMessageGlobal(textGlobal, logMessageGlobal.toString());
				break;
			case SERVER:
				// Génération des logs
				StringBuilder logMessageServer = new StringBuilder();
				logMessageServer.append("[SERVER] ");
				logMessageServer.append(StringUtil.removeAllColor(aliasServer));
				logMessageServer.append(inLive.getInLiveStr());
				logMessageServer.append(prefixGroup.getPrefixGroupStr());
				logMessageServer.append(prefixUser.getPrefixUserStr());
				logMessageServer.append(player.getDisplayName()).append(" : ");
				logMessageServer.append(StringUtil.removeAllColor(msg));

				// Création du message complet
				ComponentBuilder textServer = new ComponentBuilder();
				textServer.appendLegacy(aliasServer);
				textServer.append(inLive.getPrefix().create(), ComponentBuilder.FormatRetention.FORMATTING);
				textServer.append(prefixGroup.getPrefixGroup().create(), ComponentBuilder.FormatRetention.FORMATTING);
				textServer.append(prefixUser.getPrefixPlayer().create(), ComponentBuilder.FormatRetention.FORMATTING);
				textServer.appendLegacy(player.getDisplayName() + ChatColor.WHITE + " : ");
				textServer.append(TextComponent.fromLegacyText(msg));

				sendMessageServer(textServer, logMessageServer.toString(), player.getServer());

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
	public void sendMessageGlobal(ComponentBuilder msg, String logMsg)
	{
		// Envoie dans le chat
		ProxyServer.getInstance().getPlayers().forEach((player) -> {
			player.sendMessage(msg.create());
		});
		// Log
		ProxyServer.getInstance().getLogger().log(Level.INFO, logMsg);
	}

	// Envoi du message sur un serveur
	public void sendMessageServer(ComponentBuilder msg, String logMessage, Server server)
	{
		// Envoie dans le chat
		server.getInfo().getPlayers().forEach((player) -> {
			player.sendMessage(msg.create());
		});
		// Log
		ProxyServer.getInstance().getLogger().log(Level.INFO, logMessage);
	}

	// Envoie d'un message privé
	public void sendMessagePlayer(ProxiedPlayer playerDest, ProxiedPlayer playerExpe, String msgFull)
	{
		// Conversion en couleur si permission
		String messageColor = msgFull;
		if(this.hasChatColorPermissions(playerExpe))
		{
			messageColor = StringUtil.HEXtoText(messageColor);
		}

		// Génération de l'affichage d'un message privé
		PrivateMessageBuilder privateMessage = new PrivateMessageBuilder(playerExpe.getDisplayName(), playerDest.getDisplayName(), messageColor);

		playerDest.sendMessage(privateMessage.getMessageDest().create());
		playerExpe.sendMessage(privateMessage.getMessageExpe().create());

		// Envoie du message à la modération
		Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();
		ComponentBuilder msgPrivate = new ComponentBuilder(ChatColor.GRAY + playerExpe.getName() + " -> " + playerDest.getName() + " : ");
		msgPrivate.appendLegacy(messageColor);
		for(ProxiedPlayer player : allPlayers)
		{
			// Si le modo n'est pas déjà dans le serveur
			if(moderatorsPrivateView.isActive(player) && !player.getName().equals(playerDest.getName()) && !player.getName().equals(playerExpe.getName()))
			{
				player.sendMessage(msgPrivate.create());
			}
		}
		// Envoi dans les logs
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("[PRIVATE] ");
		logMessage.append(playerExpe.getName());
		logMessage.append(" -> ");
		logMessage.append(playerDest.getName());
		logMessage.append(" : ");
		logMessage.append(StringUtil.removeAllColor(msgFull));

		ProxyServer.getInstance().getLogger().log(Level.INFO, logMessage.toString());
	}

	public boolean hasChatColorPermissions(CommandSender sender)
	{
		return sender.hasPermission(Permissions.FUNC_CHAT_COLOR.toString()) | sender.hasPermission("*");
	}
}
