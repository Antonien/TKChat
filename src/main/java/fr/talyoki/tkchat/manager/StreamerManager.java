package fr.talyoki.tkchat.manager;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Map;
import java.util.TreeMap;

public class StreamerManager
{
	private Map<String, String> StreamerList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	public StreamerManager()
	{
	}

	// Permet d'ajouter un streamer à la liste
	public void addPlayer(String psMc, String psTw)
	{
		StreamerList.put(psMc, psTw);
	}

	// Permet de retirer un streamer de la liste
	public void remove(String psMc)
	{
		StreamerList.remove(psMc);
	}

	// Permet de récupérer le pseudo twitch en fonction du pseudo minecraft
	public String getPseudoTwitch(String psMc)
	{
		return (String) StreamerList.get(psMc);
	}

	// Permet d'afficher la liste actuelle des streamer (debug)
	public void listPlayer(CommandSender cs)
	{
		((ProxiedPlayer) cs).sendMessage(new TextComponent("Liste des joueurs en live :"));
		for(String i : StreamerList.keySet())
		{
			((ProxiedPlayer) cs).sendMessage(new TextComponent(i));
		}
	}

	// Permet de savoir si un joueur est en stream
	public boolean isInLive(Connection cs)
	{
		String pseudoMc = ((ProxiedPlayer) cs).getName();
		return StreamerList.containsKey(pseudoMc);
	}
}
