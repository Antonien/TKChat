package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.manager.Manager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SwitchServerEventListener implements Listener
{
	private ConfigManager configManager;

	public SwitchServerEventListener(Manager manager)
	{
		configManager = manager.configManager;
	}

	@EventHandler
	public void onSwitchServer(ServerSwitchEvent e)
	{
		if(e.getFrom() != null)
		{
			String msg = configManager.listWelcomeMessage.get("server_switch");
			if(!msg.equals(""))
			{
				msg = msg.replace("%player%", e.getPlayer().getDisplayName());
				msg = msg.replace("%old_serv_prefix%", configManager.listAliasGlobalPrefix.get(e.getFrom().getName()));
				msg = msg.replace("%new_serv_prefix%", configManager.listAliasGlobalPrefix.get(e.getPlayer().getServer().getInfo().getName()));
				msg = msg.replace("%old_serv_name%", e.getFrom().getName());
				msg = msg.replace("%new_serv_name%", e.getPlayer().getServer().getInfo().getName());
				ProxyServer.getInstance().broadcast(new TextComponent(msg.replace('&', '§')));
			}
		}
	}
}
