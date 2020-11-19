package fr.talyoki.tkchat.manager;

import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class Manager
{
	public LuckPermInfo luckPerms = null;
	public StreamerManager streamerManager = null;
	public LastPrivateMessageHistoryManager lastPrivateMsgHist = null;
	public ModeratorsGlobalViewManager moderatorsGlobalViewManager = null;
	public ModeratorsPrivateViewManager moderatorsPrivateViewManager = null;
	public MessageManager messageManager = null;
	public ConfigManager configManager = null;

	// Initialisation des configs
	public Manager(Plugin plugin)
	{
		// Initialisation des configs
		configManager = new ConfigManager(plugin);

		// Initialisation de l'API LuckPerms
		luckPerms = new LuckPermInfo();

		// Initialisation des variables
		streamerManager = new StreamerManager();
		lastPrivateMsgHist = new LastPrivateMessageHistoryManager();
		moderatorsGlobalViewManager = new ModeratorsGlobalViewManager();
		moderatorsPrivateViewManager = new ModeratorsPrivateViewManager();
		messageManager = new MessageManager(this);
	}
}
