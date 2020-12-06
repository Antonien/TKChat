package fr.talyoki.tkchat;

import fr.talyoki.tkchat.cmd.*;
import fr.talyoki.tkchat.listeners.*;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.utils.ConfigUtil;
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
		// Initialisation du manager
		Manager manager = new Manager(this);

		// Si le fichier de config existe pas
		if(!ConfigUtil.SaveConfigFile("config.yml", this)){
			this.onDisable();
		}

		// Si le fichier de config charge pas
		if(!manager.configManager.loadConfig())
		{
			this.onDisable();
		}

		// Events
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatEventListener(manager));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new LoginEventListener(manager));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new DisconnectEventListener(manager));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new SwitchServerEventListener(manager));

		GroupeChangeEventListener groupeChangeEventListener = new GroupeChangeEventListener(manager);
		manager.luckPerms.getAPI().getEventBus().subscribe(UserDataRecalculateEvent.class, groupeChangeEventListener::onUserTrack);

		// Commandes
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new LiveCmd(manager));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new MsgCmd(manager));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new SpyChatCmd(manager));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new RCmd(manager));
	}

	public void onDisable()
	{

	}
}
