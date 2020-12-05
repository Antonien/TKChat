package fr.talyoki.tkchat.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.xml.soap.Text;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
	/**
	 * Copies all elements from the iterable collection of originals to the
	 * collection provided.
	 *
	 * @param <T>        the collection of strings
	 * @param token      String to search for
	 * @param originals  An iterable collection of strings to filter.
	 * @param collection The collection to add matches to
	 * @return the collection provided that would have the elements copied
	 * into
	 * @throws UnsupportedOperationException if the collection is immutable
	 *                                       and originals contains a string which starts with the specified
	 *                                       search string.
	 * @throws IllegalArgumentException      if any parameter is is null
	 * @throws IllegalArgumentException      if originals contains a null element.
	 *                                       <b>Note: the collection may be modified before this is thrown</b>
	 */

	public static <T extends Collection<? super String>> T copyPartialMatches(final String token, final Iterable<String> originals, final T collection)
			throws UnsupportedOperationException, IllegalArgumentException
	{

		for(String string : originals)
		{
			if(startsWithIgnoreCase(string, token))
			{
				collection.add(string);
			}
		}

		return collection;
	}

	public static <T extends Collection<? super ProxiedPlayer>> T getPartialMatchesPlayer(final String token, final Collection<ProxiedPlayer> originals, final T collection)
			throws UnsupportedOperationException, IllegalArgumentException
	{

		for(ProxiedPlayer player : originals)
		{
			if(startsWithIgnoreCase(player.getName(), token))
			{
				collection.add(player);
			}
		}

		return collection;
	}

	/**
	 * This method uses a region to check case-insensitive equality. This
	 * means the internal array does not need to be copied like a
	 * toLowerCase() call would.
	 *
	 * @param string String to check
	 * @param prefix Prefix of string to compare
	 * @return true if provided string starts with, ignoring case, the prefix
	 * provided
	 * @throws NullPointerException     if prefix is null
	 * @throws IllegalArgumentException if string is null
	 */
	public static boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException
	{

		if(string.length() < prefix.length())
		{
			return false;
		}
		return string.regionMatches(true, 0, prefix, 0, prefix.length());
	}

	// Read file content into string with - Files.readAllBytes(Path path)
	public static String readFileAsString(String filePath)
	{
		String content = "";

		try
		{
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return content;
	}

	// Convert HEX color to chat.color
	public static String HEXtoText(String msg)
	{
		StringBuilder newText = new StringBuilder();

		String HEXreg = "#[a-fA-F0-9]{6}";

		String[] msgArray = msg.split(HEXreg);

		List<String> allMatches = new ArrayList<>();
		Matcher m = Pattern.compile(HEXreg).matcher(msg);
		while (m.find()) {
			allMatches.add(m.group());

			
		}

		/*int strLength = msgArray.length;
		int hexLength = allMatches.size();

		for(int i=0; i<strLength || i<hexLength; i++)
		{
			if(i < msgArray.length)
			{
				newText.append(msgArray[i]);
			}
			if(i < allMatches.size())
			{
				newText.append(ChatColor.of(allMatches.get(i)));
			}
		}*/

		return newText.toString();
	}
}
