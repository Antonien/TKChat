package fr.talyoki.tkchat.cmd;

import fr.talyoki.tkchat.data.ErrorMsg;
import fr.talyoki.tkchat.data.Permissions;
import fr.talyoki.tkchat.manager.LastPrivateMessageHistoryManager;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.MessageManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RCmd extends Command
{
	private LastPrivateMessageHistoryManager lastPrivateMsgHist;
	private MessageManager messageManager;

	public RCmd(Manager manager)
	{
		super("r");
		this.lastPrivateMsgHist = manager.lastPrivateMsgHist;
		this.messageManager = manager.messageManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(hasRPrivatePermissions(sender))
		{
			if(args.length > 0)
			{
				String lastDest = lastPrivateMsgHist.getPlayer(sender.getName());
				if(lastDest != null && ProxyServer.getInstance().getPlayer(lastDest) != null)
				{
					String message = "";
					// Récupération des argument pour reformer le message
					for(int i = 0; i < args.length; ++i)
					{
						message = message + args[i] + " ";
					}
					message = message.substring(0, message.length() - 1);

					// Envoi du message aux joueurs
					TextComponent msgFull = new TextComponent(message);
					messageManager.SendMessagePlayer(ProxyServer.getInstance().getPlayer(lastDest), (ProxiedPlayer) sender, msgFull, true);
				}
				else
				{
					sender.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez parlé à personne ou le joueur n'est pas connecté"));
				}
			}
			else
			{
				sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_CMD)));
			}
		}
		else
		{
			sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_PERM)));
		}
	}

	// Permissions réponse privé
	private boolean hasRPrivatePermissions(CommandSender sender)
	{
		return sender.hasPermission("*") || sender.hasPermission(Permissions.CMD_MSG_R.toString());
	}
}
