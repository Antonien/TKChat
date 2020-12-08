package fr.talyoki.tkchat.data;

import fr.talyoki.tkchat.data.message.PrefixGroup;
import fr.talyoki.tkchat.data.message.PrefixUser;

import java.util.UUID;

public class PlayerData
{
	private PrefixGroup prefixGroup = null;
	private PrefixUser prefixUser = null;
	private String playerName = null;
	private UUID uuid = null;

	public PlayerData(String playerName, UUID uuid, PrefixGroup prefixGroup, PrefixUser prefixUser)
	{
		this.playerName = playerName;
		this.uuid = uuid;
		this.prefixGroup = prefixGroup;
		this.prefixUser = prefixUser;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public PrefixGroup getPrefixGroup()
	{
		return prefixGroup;
	}

	public PrefixUser getPrefixUser()
	{
		return prefixUser;
	}
}
