package fr.talyoki.tkchat.manager;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager
{
	private Configuration configuration = null;
	private Plugin plugin = null;

	public static Map<String, String> listAliasGlobalPrefix = null;
	public static Map<String, String> listAliasServerPrefix = null;
	public static Map<String, String> listPrefix = null;
	public static Map<String, String> listWelcomeMessage = null;
	public static Map<String, String> listInfoGroup = null;

	public ConfigManager(Plugin plugin)
	{
		this.plugin = plugin;
	}

	public boolean loadConfig()
	{
		listAliasGlobalPrefix = new HashMap<>();
		listAliasServerPrefix = new HashMap<>();
		listPrefix = new HashMap<>();
		listWelcomeMessage = new HashMap<>();
		listInfoGroup = new HashMap<>();

		// Initialisation du fichier de config
		try
		{
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}

		if(configuration != null)
		{
			// Récupération des alias de serveurs
			if(!configuration.contains("alias"))
			{
				return false;
			}
			if(!configuration.contains("alias.global"))
			{
				return false;
			}
			if(!configuration.contains("alias.server"))
			{
				return false;
			}
			Configuration listAliasGlobal = configuration.getSection("alias.global");
			Configuration listAliasServer = configuration.getSection("alias.server");
			if(!listAliasGlobal.contains("default"))
			{
				return false;
			}
			if(!listAliasServer.contains("default"))
			{
				return false;
			}
			for(String key : listAliasGlobal.getKeys())
			{
				listAliasGlobalPrefix.put(key, listAliasGlobal.getString(key,""));
			}
			for(String key : listAliasServer.getKeys())
			{
				listAliasServerPrefix.put(key, listAliasServer.getString(key,""));
			}

			// Récupération des prefix
			if(!configuration.contains("prefix"))
			{
				return false;
			}
			Configuration listPrefixConfig = configuration.getSection("prefix");
			for(String key : listPrefixConfig.getKeys())
			{
				listPrefix.put(key, listPrefixConfig.getString(key, ""));
			}

			// Récupération des messages de bienvenue
			if(!configuration.contains("welcome_msg"))
			{
				return false;
			}
			Configuration listWelcomeMsg = configuration.getSection("welcome_msg");
			for(String key : listWelcomeMsg.getKeys())
			{
				listWelcomeMessage.put(key, listWelcomeMsg.getString(key, ""));
			}

			// Récupération des info de grade
			if(!configuration.contains("group_info"))
			{
				return false;
			}
			Configuration listGroup = configuration.getSection("group_info");
			for(String key : listGroup.getKeys())
			{
				listInfoGroup.put(key, listGroup.getString(key, ""));
			}
		}

		return false;
	}
}
