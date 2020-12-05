package fr.talyoki.tkchat.data;

import fr.talyoki.tkchat.manager.ConfigManager;
import fr.talyoki.tkchat.manager.StreamerManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TextInLive
{
	private TextComponent inLive = new TextComponent("");

	public TextInLive(ConfigManager configManager, StreamerManager streamerManager, ProxiedPlayer player)
	{
		if(streamerManager.isInLive(player))
		{
			String psTwitch = streamerManager.getPseudoTwitch(player.getName());
			inLive = new TextComponent(configManager.listPrefix.getOrDefault("live", "").replace('&', '§'));
			inLive.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/" + psTwitch));
			inLive.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new Text("Regarder le stream")));
		}
	}

	public TextComponent getPrefix()
	{
		return inLive;
	}
}
