package fr.talyoki.tkchat.data.message;

import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.manager.StreamerManager;
import fr.talyoki.tkchat.utils.StringUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TextInLive
{
	private ComponentBuilder inLive = new ComponentBuilder("");
	private String inLiveStr = "";

	public TextInLive(ConfigManager configManager, StreamerManager streamerManager, ProxiedPlayer player)
	{
		if(streamerManager.isInLive(player))
		{
			// Récupération du pseudo twitch
			String psTwitch = streamerManager.getPseudoTwitch(player.getName());

			// Récupération du préfix de la config
			String prefixLive = configManager.listPrefix.getOrDefault("live", "");

			// Génération du component sans couleurs
			inLiveStr = StringUtil.removeAllColor(prefixLive);

			// Génération du component
			inLive.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/" + psTwitch));
			inLive.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Regarder le stream")));
			inLive.appendLegacy(StringUtil.HEXtoText(prefixLive));
		}
	}

	public ComponentBuilder getPrefix()
	{
		return inLive;
	}

	public String getInLiveStr()
	{
		return inLiveStr;
	}
}
