package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.PlayerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectEventListener implements Listener
{
	private PlayerManager playerManager;

	public DisconnectEventListener(Manager manager)
	{
		this.playerManager = manager.playerManager;
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent e)
	{
		// Suppression des données du joueur
		playerManager.removePlayer(e.getPlayer());

		String msg = ConfigManager.listWelcomeMessage.get("server_disconnect");
		if(!msg.equals(""))
		{
			msg = msg.replace("%player%", e.getPlayer().getDisplayName());
			ProxyServer.getInstance().broadcast(new TextComponent(msg.replace('&', '§')));
		}
	}
}
