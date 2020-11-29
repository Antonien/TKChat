package fr.talyoki.tkchat.luckperm;

import java.util.*;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.query.QueryOptions;

public class LuckPermInfo
{
	LuckPerms luckPerms = LuckPermsProvider.get();

	public LuckPermInfo()
	{
	}

	// Permet de créer un joueur avec un uuid
	public User setUserLpByUUID(UUID uuid)
	{
		User user = luckPerms.getUserManager().getUser(uuid);
		return user;
	}

	// Permet d'obtenir le prefix d'un joueur
	public String getPrefixGroup(User user)
	{
		String prefix = "";
		Collection<Group> list = user.getInheritedGroups(QueryOptions.nonContextual());

		Group group = list.iterator().next();
		for(Group node : list)
		{
			if(node.getWeight().isPresent())
			{
				if(node.getWeight().getAsInt() > group.getWeight().getAsInt())
				{
					group = node;
				}
			}
		}

		Collection<PrefixNode> listprefix = group.getNodes(NodeType.PREFIX);
		prefix = ((PrefixNode) listprefix.toArray()[0]).getMetaValue();

		return prefix;
	}

	public String getPrefixUser(User user)
	{
		String prefix = "";
		Collection<PrefixNode> listprefix = user.getNodes(NodeType.PREFIX);
		if(listprefix.size() > 0)
		{
			prefix = ((PrefixNode) listprefix.toArray()[0]).getMetaValue();
		}

		return prefix;
	}

	// Permet de récupérer l'api
	public LuckPerms getAPI()
	{
		return this.luckPerms;
	}
}
