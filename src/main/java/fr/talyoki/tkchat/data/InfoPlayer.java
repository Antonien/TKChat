package fr.talyoki.tkchat.data;

import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoPlayer
{
	private BaseComponent[] prefixGroup = null;
	private BaseComponent[] prefixUser = null;

	public InfoPlayer(LuckPermInfo luckPerms, ConfigManager configManager, ProxiedPlayer player)
	{
		// Déclaration du joueur version API Luckperms
		User user = luckPerms.setUserLpByUUID(player.getUniqueId());

		// Récupération du prefix du joueur
		prefixGroup = TextComponent.fromLegacyText(StringUtil.HEXtoText(luckPerms.getPrefixUser(user)).replace('&', '§'));
		prefixUser = TextComponent.fromLegacyText(StringUtil.HEXtoText(luckPerms.getPrefixUser(user)).replace('&', '§'));
	}

	public BaseComponent[] getPrefixGroup()
	{
		return prefixGroup;
	}

	public BaseComponent[] getPrefixPlayer()
	{
		return prefixUser;
	}
}
