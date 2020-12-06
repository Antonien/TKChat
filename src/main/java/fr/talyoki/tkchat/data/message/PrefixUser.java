package fr.talyoki.tkchat.data.message;

import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixUser
{
	private ComponentBuilder prefixUser = new ComponentBuilder();
	private String prefixUserStr = null;

	public PrefixUser(LuckPermInfo luckPerms, ConfigManager configManager, ProxiedPlayer player)
	{
		// Déclaration du joueur version API Luckperms
		User user = luckPerms.setUserLpByUUID(player.getUniqueId());

		// Récupération du groupe du joueur
		String luckpermPrefixUser = luckPerms.getPrefixUser(user);

		// Génération du groupe sans couleurs
		prefixUserStr = StringUtil.removeAllColor(luckpermPrefixUser);

		// Récupération du texte des config
		String textInfo = StringUtil.HEXtoText(configManager.listInfoGroup.get(luckPerms.getGroupName(user)));

		// Récupération du prefix du joueur
		prefixUser.appendLegacy(StringUtil.HEXtoText(luckpermPrefixUser));
	}

	public ComponentBuilder getPrefixPlayer()
	{
		return prefixUser;
	}

	public String getPrefixUserStr()
	{
		return prefixUserStr;
	}
}
