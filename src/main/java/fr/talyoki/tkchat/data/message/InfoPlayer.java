package fr.talyoki.tkchat.data;

import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InfoPlayer
{
	private ComponentBuilder prefixGroup = new ComponentBuilder();
	private BaseComponent[] prefixUser = null;

	public InfoPlayer(LuckPermInfo luckPerms, ConfigManager configManager, ProxiedPlayer player)
	{
		// Déclaration du joueur version API Luckperms
		User user = luckPerms.setUserLpByUUID(player.getUniqueId());
		System.out.println(luckPerms.getPrefixGroup(user));
		// Récupération du prefix du joueur
		prefixGroup.appendLegacy(StringUtil.HEXtoText(luckPerms.getPrefixGroup(user)));

		prefixGroup.event(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new Text("info grade")));

		prefixUser = TextComponent.fromLegacyText(StringUtil.HEXtoText(luckPerms.getPrefixUser(user)));
	}

	public ComponentBuilder getPrefixGroup()
	{
		return prefixGroup;
	}

	public BaseComponent[] getPrefixPlayer()
	{
		return prefixUser;
	}
}
