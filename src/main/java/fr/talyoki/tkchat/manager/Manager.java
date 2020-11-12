package fr.talyoki.tkchat.manager;

import fr.talyoki.tkchat.luckperm.LuckPermInfo;

public class Manager
{
	// Initialisation de l'API LuckPerms
	public LuckPermInfo luckPerms = new LuckPermInfo();

	// Initialisation des variables
	public StreamerManager streamerManager = new StreamerManager();
	public LastPrivateMessageHistoryManager lastPrivateMsgHist = new LastPrivateMessageHistoryManager();
	public ModeratorsGlobalViewManager moderatorsGlobalViewManager = new ModeratorsGlobalViewManager();
	public ModeratorsPrivateViewManager moderatorsPrivateViewManager = new ModeratorsPrivateViewManager();
	public MessageManager messageManager = new MessageManager(this);
}
