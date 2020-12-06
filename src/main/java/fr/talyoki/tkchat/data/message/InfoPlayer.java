package fr.talyoki.tkchat.data.message;

import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InfoPlayer
{
	private ComponentBuilder prefixGroup = new ComponentBuilder();
	private ComponentBuilder prefixUser = new ComponentBuilder();

	public InfoPlayer(LuckPermInfo luckPerms, ConfigManager configManager, ProxiedPlayer player)
	{
		// Déclaration du joueur version API Luckperms
		User user = luckPerms.setUserLpByUUID(player.getUniqueId());

		// Récupération du texte des config
		String textInfo = configManager.listInfoGroup.get(luckPerms.getGroupName(user));

		// Récupération du prefix du joueur
		prefixGroup.event(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new Text(textInfo)));
		prefixGroup.appendLegacy(StringUtil.HEXtoText(luckPerms.getPrefixGroup(user)));

		prefixUser.appendLegacy(StringUtil.HEXtoText(luckPerms.getPrefixUser(user)));
	}

	public ComponentBuilder getPrefixGroup()
	{
		return prefixGroup;
	}

	public ComponentBuilder getPrefixPlayer()
	{
		return prefixUser;
	}
}
