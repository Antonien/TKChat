package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.ModeratorsGlobalViewManager;
import net.luckperms.api.event.user.UserDataRecalculateEvent;

public class EventGroupChange
{
	private ModeratorsGlobalViewManager moderatorsGlobalViewManager;

	public EventGroupChange(ModeratorsGlobalViewManager moderatorsGlobalViewManager)
	{
		this.moderatorsGlobalViewManager = moderatorsGlobalViewManager;
	}

	public void onUserTrack(UserDataRecalculateEvent e)
	{
		moderatorsGlobalViewManager.verifModo(e.getUser().getUsername());
	}
}
