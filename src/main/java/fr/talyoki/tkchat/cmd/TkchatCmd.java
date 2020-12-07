package fr.talyoki.tkchat.cmd;

import fr.talyoki.tkchat.data.ErrorMsg;
import fr.talyoki.tkchat.data.Permissions;
import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.manager.Manager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.logging.Level;

public class TkchatCmd extends Command
{
	private ConfigManager configManager;

	public TkchatCmd(Manager manager)
	{
		super("tkchat");
		this.configManager = manager.configManager;
	}

	public void execute(CommandSender sender, String[] args)
	{
		// Si la commande n'a pas d'argument
		if(args.length < 1)
		{
			sender.sendMessage(new TextComponent(ErrorMsg.ERROR_CMD.toString()));
			return;
		}

		// Si le premier argument est reloadconfig
		if(args[0].equals("reloadconfig"))
		{
			// Si le joueur n'a pas les permissions
			if(!hasReloadPermission(sender))
			{
				sender.sendMessage(new TextComponent(ErrorMsg.ERROR_PERM.toString()));
				return;
			}
			else
			{
				// Rechargement des config
				configManager.loadConfig();

				ProxyServer.getInstance().getLogger().log(Level.INFO, "[TKchat] Fichier de configuration rechargé");
				if(sender instanceof ProxiedPlayer)
				{
					// Envoie d'un message dans le chat de l'envoyeur si c'est un joueur
					sender.sendMessage(new TextComponent(ChatColor.GOLD + "[TKchat] Fichier de configuration rechargé"));
				}
			}
		}
	}

	// Permission reload
	public boolean hasReloadPermission(CommandSender sender)
	{
		return sender.hasPermission("*") || sender.hasPermission(Permissions.CMD_TKCHAT_RELOADCONFIG.toString());
	}
}
