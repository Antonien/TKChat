package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.ModeratorsGlobalViewManager;
import fr.talyoki.tkchat.manager.ModeratorsPrivateViewManager;
import net.luckperms.api.event.user.UserDataRecalculateEvent;

public class GroupeChangeEventListener
{
	private ModeratorsGlobalViewManager moderatorsGlobalView;
	private ModeratorsPrivateViewManager moderatorsPrivateView;

	public GroupeChangeEventListener(Manager manager)
	{
		this.moderatorsGlobalView = manager.moderatorsGlobalViewManager;
		this.moderatorsPrivateView = manager.moderatorsPrivateViewManager;
	}

	public void onUserTrack(UserDataRecalculateEvent e)
	{
		// Vérifie si le joueur à toujours les permissions
		moderatorsGlobalView.verifModo(e.getUser().getUsername());
		moderatorsPrivateView.verifModo(e.getUser().getUsername());
	}
}
