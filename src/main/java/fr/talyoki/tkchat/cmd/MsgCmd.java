package fr.talyoki.tkchat.cmd;

import fr.talyoki.tkchat.data.Permissions;
import fr.talyoki.tkchat.manager.LastPrivateMessageHistoryManager;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.MessageManager;
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

public class MsgCmd extends Command implements TabExecutor
{
	private LastPrivateMessageHistoryManager lastPrivateMsgHist;
	private MessageManager messageManager;

	public MsgCmd(Manager manager)
	{
		super("msg");
		this.lastPrivateMsgHist = manager.lastPrivateMsgHist;
		this.messageManager = manager.messageManager;
	}

	// Event de commande
	public void execute(CommandSender commandSender, String[] args)
	{
		// Vérification des permissions
		if(this.hasSendPrivatePermissions(commandSender))
		{
			if(args.length >= 2)
			{
				// Si la commande a au moins un argument
				final List<ProxiedPlayer> resultPlayers = new ArrayList<ProxiedPlayer>();
				StringUtil.getPartialMatchesPlayer(args[0], ProxyServer.getInstance().getPlayers(), resultPlayers);
				if(resultPlayers.size() == 1)
				{
					String message = "";
					// Récupération des argument pour reformer le message
					for(int i = 1; i < args.length; ++i)
					{
						message = message + args[i] + " ";
					}
					message = message.substring(0, message.length() - 1);

					// Envoi du message en privé
					messageManager.sendMessagePlayer(resultPlayers.get(0), (ProxiedPlayer) commandSender, message);

					// Gestion de l'historique
					lastPrivateMsgHist.addPlayer(commandSender.getName(), resultPlayers.get(0).getName());
				}
				else
				{
					commandSender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande ou joueur absent"));
				}
			}
			else
			{
				commandSender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
			}
		}
		else
		{
			commandSender.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission de parler en privé"));
		}
	}

	// Permissions message privé
	private boolean hasSendPrivatePermissions(CommandSender sender)
	{
		return sender.hasPermission("*") || sender.hasPermission(Permissions.CMD_MSG_PRIVATE.toString());
	}

	// Permissions message privé couleur
	private boolean hasChatColorPermissions(CommandSender sender)
	{
		return sender.hasPermission("*") || sender.hasPermission(Permissions.FUNC_CHAT_COLOR.toString());
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
