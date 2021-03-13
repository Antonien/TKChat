package fr.talyoki.tkchat.data.message;

import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class PrefixGroup
{
	private ComponentBuilder prefixGroup = new ComponentBuilder();
	private String prefixGroupStr = null;

	public PrefixGroup(String luckpermGroupName, String luckpermGroupPrefix)
	{
		// Déclaration du joueur version API Luckperms
		//User user = luckPerms.setUserLpByUUID(player.getUniqueId());

		// Récupération du groupe du joueur
		//String luckpermGroup = luckPerms.getPrefixGroup(user);

		// Génération du prefix sans couleurs
		prefixGroupStr = StringUtil.removeAllColor(luckpermGroupPrefix);

		// Récupération du texte des config
		String textInfo = ConfigManager.listInfoGroup.getOrDefault(luckpermGroupName, "");

		// Récupération du prefix du joueur
		prefixGroup.event(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder().appendLegacy(textInfo).create()));
		prefixGroup.appendLegacy(StringUtil.HEXtoText(luckpermGroupPrefix));
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
