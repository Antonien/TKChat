package fr.talyoki.tkchat.data.message;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PrivateMessageBuilder
{
	private ComponentBuilder messageDest = new ComponentBuilder();
	private ComponentBuilder messageExpe = new ComponentBuilder();

	public PrivateMessageBuilder(String playerExpe, String playerDest, String msg)
	{
		messageDest.append(ChatColor.GOLD + playerExpe + ChatColor.DARK_GRAY + " -> " + ChatColor.GOLD + "Toi" + ChatColor.DARK_GRAY + " : " + ChatColor.WHITE);
		messageDest.appendLegacy(msg);

		messageExpe.append(ChatColor.GOLD +"Toi" + ChatColor.DARK_GRAY + " -> " + ChatColor.GOLD + playerDest + ChatColor.DARK_GRAY + " : " + ChatColor.WHITE);
		messageExpe.appendLegacy(msg);
	}

	public ComponentBuilder getMessageDest()
	{
		return messageDest;
	}

	public ComponentBuilder getMessageExpe()
	{
		return messageExpe;
	}
}
