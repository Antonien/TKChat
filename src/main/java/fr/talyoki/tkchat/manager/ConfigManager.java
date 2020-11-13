package fr.talyoki.tkchat.manager;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager
{
	private Configuration configuration = null;
	private Plugin plugin = null;

	public Map<String, String> listAliasGlobalPrefix = new HashMap<>();
	public Map<String, String> listAliasServerPrefix = new HashMap<>();
	public Map<String, String> listPrefix = new HashMap<>();

	public ConfigManager(Plugin plugin)
	{
		this.plugin = plugin;
	}

	public boolean loadConfig()
	{
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
		}

		return false;
	}
}
