package fr.talyoki.tkchat;

import fr.talyoki.tkchat.cmd.ChatCmd;
import fr.talyoki.tkchat.cmd.LiveCmd;
import fr.talyoki.tkchat.cmd.MsgCmd;
import fr.talyoki.tkchat.cmd.RCmd;
import fr.talyoki.tkchat.listeners.EventChatEvent;
import fr.talyoki.tkchat.listeners.EventGroupChange;
import fr.talyoki.tkchat.listeners.EventLoginEvent;
import fr.talyoki.tkchat.manager.Manager;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class TKchat extends Plugin
{
	public TKchat()
	{
	}

	public void onEnable()
	{
		Manager manager = new Manager();

		// Events
		ProxyServer.getInstance().getPluginManager().registerListener(this, new EventChatEvent(manager));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new EventLoginEvent(manager));

		EventGroupChange eventGroupChange = new EventGroupChange(manager);
		manager.luckPerms.getAPI().getEventBus().subscribe(UserDataRecalculateEvent.class, eventGroupChange::onUserTrack);

		// Commandes
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new LiveCmd(manager));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new MsgCmd(manager));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatCmd(manager));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new RCmd(manager));
	}

	public void onDisable()
	{
	}
}
