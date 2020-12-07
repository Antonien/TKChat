package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.manager.Manager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ProxyReloadEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

public class ReloadEventListener implements Listener
{
	private ConfigManager configManager;

	public ReloadEventListener(Manager manager)
	{
		this.configManager = manager.configManager;
	}

	@EventHandler
	public void onReloadEvent(ProxyReloadEvent e)
	{
		configManager.loadConfig();
		ProxyServer.getInstance().getLogger().log(Level.INFO, "[TKchat] Fichier de configuration rechargé");
	}
}
