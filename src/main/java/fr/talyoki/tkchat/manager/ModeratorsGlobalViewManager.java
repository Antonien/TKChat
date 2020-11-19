package fr.talyoki.tkchat.manager;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashSet;
import java.util.Set;

public class ModeratorsGlobalViewManager
{
	private Set<String> moderatorsGlobalView = new HashSet<String>();

	// Permet d'actualiser le statu d'un modo
	public void verifModo(String playerName)
	{
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
		if(player != null)
		{
			if(player.hasPermission("tkChat.spychat.global"))
			{
				addModo(player.getName());
			}
			else
			{
				removeModo(player.getName());
			}
		}
	}

	// Permet d'ajouter un joueur a la liste
	public boolean addModo(String player)
	{
		return moderatorsGlobalView.add(player);
	}

	// Permet de retirer un joueur de la liste
	public boolean removeModo(String player)
	{
		return moderatorsGlobalView.remove(player);
	}

	// Permet de savoir si un modérateur veux voir les messages de tous les serveurs
	public boolean isActive(ProxiedPlayer player)
	{
		String playerName = player.getName();
		return moderatorsGlobalView.contains(playerName);
	}

	// Permet d'afficher la liste actuelle des modo (debug)
	public void listPlayer(CommandSender cs)
	{
		((ProxiedPlayer) cs).sendMessage(new TextComponent("Liste des modérateurs (server) :"));
		for(String i : moderatorsGlobalView)
		{
			((ProxiedPlayer) cs).sendMessage(new TextComponent(i));
		}
	}
}
