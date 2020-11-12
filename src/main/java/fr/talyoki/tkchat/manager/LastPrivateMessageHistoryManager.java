package fr.talyoki.tkchat.manager;

import java.util.HashMap;
import java.util.Map;

public class LastPrivateMessageHistoryManager
{
	private Map<String, String> lastPrivateMsg = new HashMap<>();

	public LastPrivateMessageHistoryManager()
	{

	}

	// Permet d'ajouter un couple de joueur à l'historique
	public void addPlayer(String expe, String dest)
	{
		lastPrivateMsg.put(expe, dest);
		lastPrivateMsg.put(dest, expe);
	}

	// Permet de get le joueur destinataire
	public String getPlayer(String player)
	{
		return lastPrivateMsg.get(player);
	}
}
