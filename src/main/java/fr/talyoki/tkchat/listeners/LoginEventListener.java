package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.*;
import fr.talyoki.tkchat.utils.StringUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class LoginEventListener implements Listener
{
	private ModeratorsGlobalViewManager moderatorsGlobalView;
	private ModeratorsPrivateViewManager moderatorsPrivateView;
	private PlayerManager playerManager;

	private static Set<String> newTempPlayers = new HashSet<String>();

	// Récupération des variables
	public LoginEventListener(Manager manager)
	{
		moderatorsGlobalView = manager.moderatorsGlobalViewManager;
		moderatorsPrivateView = manager.moderatorsPrivateViewManager;
		playerManager = manager.playerManager;
	}

	// Lorsqu'un joueur se connecte ...
	@EventHandler
	public void onLoginEvent(PostLoginEvent e)
	{
		// Récupération du joueur
		ProxiedPlayer player = e.getPlayer();

		// Récupération des données du joueur
		playerManager.addPlayer(player);

		String locations = StringUtil.readFileAsString(ProxyServer.getInstance().getPluginsFolder() + "/../locations.yml");
		if(locations.contains("'" + player.getName() + ";") || newTempPlayers.contains(player.getName()))
		{
			// Message lorsqu'un joueur se connecte
			String msg = ConfigManager.listWelcomeMessage.get("server_connect").replace("%player%", player.getDisplayName());
			ProxyServer.getInstance().broadcast(new TextComponent(msg.replace('&', '§')));
		}
		else
		{
			// Message lorsqu'un joueur se connecte ET il est nouveau
			newTempPlayers.add(player.getName());
			String msg = ConfigManager.listWelcomeMessage.get("server_new").replace("%player%", player.getDisplayName());
			ProxyServer.getInstance().broadcast(new TextComponent(msg.replace('&', '§')));
		}

		// On actualise la liste des modos pour ModeratorsView
		if(player.hasPermission("tkChat.spychat.global"))
		{
			// Si il a la permission on l'ajoute par default
			if(moderatorsGlobalView.addModo(e.getPlayer().getName()))
			{
				ProxyServer.getInstance().getLogger().log(Level.INFO,
						"[TKchat] Le joueur " + player.getDisplayName() + " a été ajouté a la liste de modération du chat");
			}
			else
			{
				ProxyServer.getInstance().getLogger().log(Level.INFO,
						"[TKchat] Le joueur " + player.getDisplayName() + " n'a pas pu être ajouté a la liste de modération du chat");
			}
		}
	}

	// Lorsqu'un joueur se déconnecte
	@EventHandler
	public void onDeconnectEvent(PlayerDisconnectEvent e)
	{
		// Récupération du joueur
		ProxiedPlayer player = e.getPlayer();

		// On le supprimer de la liste modo
		moderatorsGlobalView.removeModo(player.getName());
		moderatorsPrivateView.removeModo(player.getName());
	}
}
