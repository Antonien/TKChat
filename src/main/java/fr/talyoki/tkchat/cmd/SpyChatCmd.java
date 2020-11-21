package fr.talyoki.tkchat.cmd;

import fr.talyoki.tkchat.data.ErrorMsg;
import fr.talyoki.tkchat.data.Permissions;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.ModeratorsGlobalViewManager;
import fr.talyoki.tkchat.manager.ModeratorsPrivateViewManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SpyChatCmd extends Command
{
	private ModeratorsGlobalViewManager moderatorsGlobalView;
	private ModeratorsPrivateViewManager moderatorsPrivateView;

	public SpyChatCmd(Manager manager)
	{
		super("spychat");
		this.moderatorsGlobalView = manager.moderatorsGlobalViewManager;
		this.moderatorsPrivateView = manager.moderatorsPrivateViewManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(args.length >= 1)
		{
			// Commandes pour le debug
			switch(args[0])
			{
				case "list":
					if(this.hasModerateListPermissions(sender))
					{
						if(args.length >= 2)
						{
							if(args[1].equals("global"))
							{
								moderatorsGlobalView.listPlayer(sender);
							}
							else if(args[1].equals("private"))
							{
								moderatorsPrivateView.listPlayer(sender);
							}
							else
							{
								sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_CMD)));
							}
						}
					}
					else
					{
						sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_PERM)));
					}
					break;
				case "global":
					if(this.hasModerateChatPermissions(sender))
					{
						// Commandes pour la modération du chat
						if(moderatorsGlobalView.isActive((ProxiedPlayer) sender))
						{
							// Si le joueur est deja enregistré
							if(moderatorsGlobalView.removeModo(sender.getName()))
							{
								// Message de confirmation
								sender.sendMessage(new TextComponent(ChatColor.GREEN + "Vous avez désactivé l'affichage des messages cross serveur"));
							}
						}
						else
						{
							// Si le joueur n'est pas enregistré
							if(moderatorsGlobalView.addModo(sender.getName()))
							{
								// Message de confirmation
								sender.sendMessage(new TextComponent(ChatColor.GREEN + "Vous pouvez maintenant voir les messages cross serveur"));
							}
						}
					}
					else
					{
						sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_PERM)));
					}
					break;
				case "private":
					if(this.hasModeratePrivateChatPermissions(sender))
					{
						if(moderatorsPrivateView.isActive((ProxiedPlayer) sender))
						{
							// Si le joueur est deja enregistré
							if(moderatorsPrivateView.removeModo(sender.getName()))
							{
								// Message de confirmation
								sender.sendMessage(new TextComponent(ChatColor.GREEN + "Vous avez désactivé l'affichage des messages privés"));
							}
						}
						else
						{
							// Si le joueur n'est pas enregistré
							if(moderatorsPrivateView.addModo(sender.getName()))
							{
								// Message de confirmation
								sender.sendMessage(new TextComponent(ChatColor.GREEN + "Vous pouvez maintenant voir les messages privés"));
							}
						}
					}
					else
					{
						sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_PERM)));
					}
					break;
				default:
					sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_CMD)));
					break;
			}
		}
		else
		{
			sender.sendMessage(new TextComponent(String.valueOf(ErrorMsg.ERROR_CMD)));
		}
	}

	// Permissions chat modo
	private boolean hasModerateChatPermissions(CommandSender sender)
	{
		return sender.hasPermission(Permissions.CMD_SPYCHAT_GLOBAL.toString());
	}

	private boolean hasModeratePrivateChatPermissions(CommandSender sender)
	{
		return sender.hasPermission(Permissions.CMD_SPYCHAT_PRIVATE.toString());
	}

	private boolean hasModerateListPermissions(CommandSender sender)
	{
		return sender.hasPermission(Permissions.CMD_SPYCHAT_LIST.toString());
	}
}
