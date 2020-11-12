package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.ModeratorsGlobalViewManager;
import fr.talyoki.tkchat.manager.ModeratorsPrivateViewManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

public class EventLoginEvent implements Listener
{
	private ModeratorsGlobalViewManager moderatorsGlobalView;
	private ModeratorsPrivateViewManager moderatorsPrivateView;

	// Récupération des variables
	public EventLoginEvent(Manager manager)
	{
		moderatorsGlobalView = manager.moderatorsGlobalViewManager;
		moderatorsPrivateView = manager.moderatorsPrivateViewManager;
	}

	// Lorsqu'un joueur se connecte ...
	@EventHandler
	public void onLoginEvent(PostLoginEvent e)
	{
		// Récupération du joueur
		ProxiedPlayer player = e.getPlayer();

		// On actualise la liste des modos pour ModeratorsView
		if(player.hasPermission("tkChat.chat.modo"))
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
