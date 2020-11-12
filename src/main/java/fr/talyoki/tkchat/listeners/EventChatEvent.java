package fr.talyoki.tkchat.listeners;

import fr.talyoki.tkchat.data.MsgScope;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.MessageManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class EventChatEvent implements Listener
{
	private MessageManager messageManager;

	public EventChatEvent(Manager manager)
	{
		this.messageManager = manager.messageManager;
	}

	// Si un joueur envoie un message ...
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(ChatEvent e)
	{
		if(!e.isCommand())
		{
			// Annlation de l'event
			e.setCancelled(true);

			// Création de la variable player
			ProxiedPlayer player = (ProxiedPlayer) e.getSender();

			String msg = e.getMessage();

			// Détection du type de message
			if(msg.startsWith("!") && msg.length() > 1)
			{
				if(this.hasSendServerPermissions(player))
				{
					// Supression du "!" et de l'éventuel espace
					if(msg.startsWith("! "))
					{
						msg = msg.substring(2);
					}
					else
					{
						msg = msg.substring(1);
					}

					// Envoi du message en mode serveur
					messageManager.SendMessage(player, msg, MsgScope.SERVER);
				}
				else
				{
					// Le joueur n'a pas la permission de parler en server
					player.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission de parler en local"));
				}
			}
			else if(this.hasSendGlobalPermissions(player))
			{
				// Message en mode local
				messageManager.SendMessage(player, msg, MsgScope.GLOBAL);
			}
			else
			{
				// Le joueur n'a pas la permission de parler en global
				player.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission de parler en global"));
			}
		}

	}

	// Permission message global
	private boolean hasSendGlobalPermissions(CommandSender sender)
	{
		return sender.hasPermission("tkChat.msg.global");
	}

	// Permission message serveur
	private boolean hasSendServerPermissions(CommandSender sender)
	{
		return sender.hasPermission("tkChat.msg.server");
	}
}