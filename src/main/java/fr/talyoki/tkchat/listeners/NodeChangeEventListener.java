package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.ModeratorsGlobalViewManager;
import fr.talyoki.tkchat.manager.ModeratorsPrivateViewManager;
import fr.talyoki.tkchat.manager.PlayerManager;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.md_5.bungee.api.ProxyServer;

public class NodeChangeEventListener
{
	private ModeratorsGlobalViewManager moderatorsGlobalView = null;
	private ModeratorsPrivateViewManager moderatorsPrivateView = null;
	private PlayerManager playerManager = null;

	public NodeChangeEventListener(Manager manager)
	{
		this.moderatorsGlobalView = manager.moderatorsGlobalViewManager;
		this.moderatorsPrivateView = manager.moderatorsPrivateViewManager;
		this.playerManager = manager.playerManager;
	}

	public void onUserTrack(UserDataRecalculateEvent e)
	{
		// Mise a jour de la liste des données de joueurs
		System.out.println(e.getUser().getUsername());

		playerManager.addPlayer(ProxyServer.getInstance().getPlayer(e.getUser().getUsername()));

		// Vérifie si le joueur à toujours les permissions
		moderatorsGlobalView.verifModo(e.getUser().getUsername());
		moderatorsPrivateView.verifModo(e.getUser().getUsername());
	}
}
