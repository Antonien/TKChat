package fr.talyoki.tkchat.data.message;

import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixGroup
{
	private ComponentBuilder prefixGroup = new ComponentBuilder();
	private String prefixGroupStr = null;

	public PrefixGroup(LuckPermInfo luckPerms, ConfigManager configManager, ProxiedPlayer player)
	{
		// Déclaration du joueur version API Luckperms
		User user = luckPerms.setUserLpByUUID(player.getUniqueId());

		// Récupération du groupe du joueur
		String luckpermGroup = luckPerms.getPrefixGroup(user);

		// Génération du prefix sans couleurs
		prefixGroupStr = StringUtil.removeAllColor(luckpermGroup);

		// Récupération du texte des config
		String textInfo = StringUtil.HEXtoText(configManager.listInfoGroup.get(luckPerms.getGroupName(user)));

		// Récupération du prefix du joueur
		prefixGroup.event(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder().appendLegacy(textInfo).create()));
		prefixGroup.appendLegacy(StringUtil.HEXtoText(luckpermGroup));
	}

	public ComponentBuilder getPrefixGroup()
	{
		return prefixGroup;
	}

	public String getPrefixGroupStr()
	{
		return prefixGroupStr;
	}
}
