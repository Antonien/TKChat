package fr.talyoki.tkchat.cmd;

import fr.talyoki.tkchat.data.Permissions;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.StreamerManager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LiveCmd extends Command implements TabExecutor
{
	StreamerManager streamerManager = null;

	public LiveCmd(Manager manager)
	{
		super("live");
		this.streamerManager = manager.streamerManager;
	}

	// Envoie d'une commande
	public void execute(CommandSender commandSender, String[] args)
	{
		if(this.hasLivePermissions(commandSender))
		{
			if(args.length >= 1)
			{
				// Commande pour ajouter un streamer en live
				if(args.length >= 1 && args[0].equals("up"))
				{
					if(args.length >= 3 && !args[1].isEmpty() && !args[2].isEmpty())
					{
						this.streamerManager.addPlayer(args[1], args[2]);
						commandSender.sendMessage(new TextComponent(ChatColor.RED + args[1] + ChatColor.BLUE + " est maintenant en live"));
					}
					else
					{
						commandSender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
					}
					// Commande pour supprimer un streamer en live
				}
				else if(args.length >= 1 && args[0].equals("down"))
				{
					if(args.length >= 2)
					{
						this.streamerManager.remove(args[1]);
						commandSender.sendMessage(new TextComponent(ChatColor.RED + args[1] + ChatColor.BLUE + " n'est plus en live"));
					}
					else
					{
						commandSender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
					}
					// Commande pour debuger la liste actuelle
				}
				else if(args.length >= 1 && args[0].equals("list"))
				{
					this.streamerManager.listPlayer(commandSender);
				}
				else
				{
					commandSender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
				}
			}
			else
			{
				commandSender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
			}
		}
		else
		{
			commandSender.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission"));
		}
	}

	// Permission pour gérer les lives
	private boolean hasLivePermissions(CommandSender sender)
	{
		return sender.hasPermission("*") || sender.hasPermission(Permissions.CMD_LIVE.toString());
	}

	// auto completion
	@Override
	public Iterable<String> onTabComplete(CommandSender commandSender, String[] args)
	{
		// Récupération de la liste des joueurs
		Collection<ProxiedPlayer> playerList = ProxyServer.getInstance().getPlayers();

		if(args.length == 1)
		{
			List<String> list = new ArrayList<>();

			list.add("up");
			list.add("down");

			final List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], list, completions);

			return completions;
		}

		if(args.length == 2)
		{
			List<String> list = new ArrayList<>();
			for(ProxiedPlayer player:playerList)
			{
				list.add(player.getName());
			}

			final List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], list, completions);

			return completions;
		}

		return new ArrayList<>();
	}
}
