package fr.talyoki.tkchat.utils;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;

public class ConfigUtil
{
	public static boolean SaveConfigFile(String fileName, Plugin plugin)
	{
		if(!plugin.getDataFolder().exists())
		{
			plugin.getDataFolder().mkdir();
		}
		File configFile = new File(plugin.getDataFolder(), fileName);
		if(!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
				try(InputStream is = plugin.getResourceAsStream(fileName); OutputStream os = new FileOutputStream(configFile))
				{
					ByteStreams.copy(is, os);
				}
				catch(IOException e)
				{
					e.printStackTrace();
					return false;
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
}
