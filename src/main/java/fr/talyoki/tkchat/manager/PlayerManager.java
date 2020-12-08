package fr.talyoki.tkchat.manager;

import fr.talyoki.tkchat.data.PlayerData;
import fr.talyoki.tkchat.data.message.PrefixGroup;
import fr.talyoki.tkchat.data.message.PrefixUser;
import fr.talyoki.tkchat.luckperm.LuckPermInfo;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager
{
	private LuckPermInfo luckPermInfo = null;
	private Map<String, PlayerData> listPlayerData = new HashMap<>();

	public PlayerManager(LuckPermInfo luckPermInfo)
	{
		this.luckPermInfo = luckPermInfo;
	}

	public void addPlayer(ProxiedPlayer player)
	{
		User user = luckPermInfo.setUserLpByUUID(player.getUniqueId());
		PrefixGroup prefixGroup = new PrefixGroup(luckPermInfo.getGroupName(user), luckPermInfo.getPrefixGroup(user));
		PrefixUser prefixUser = new PrefixUser(luckPermInfo.getPrefixUser(user));

		listPlayerData.put(player.getName(), new PlayerData(player.getName(), player.getUniqueId(), prefixGroup, prefixUser));
	}

	public void removePlayer(ProxiedPlayer player)
	{
		removePlayer(player.getName());
	}

	public void removePlayer(String playerName)
	{
		listPlayerData.remove(playerName);
	}

	public Map<String, PlayerData> getListPlayersData()
	{
		return listPlayerData;
	}

	public PlayerData getPlayerData(String playerName)
	{
		return listPlayerData.get(playerName);
	}
}
