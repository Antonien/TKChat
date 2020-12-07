package fr.talyoki.tkchat.data;

public enum Permissions
{
	CMD_LIVE("live"),
	FUNC_MSG_GLOBAL("msg.global"),
	FUNC_MSG_SERVER("msg.serveur"),
	CMD_MSG_PRIVATE("msg.private"),
	CMD_MSG_R("msg.r"),
	CMD_SPYCHAT_GLOBAL("spychat.global"),
	CMD_SPYCHAT_PRIVATE("spychat.private"),
	CMD_SPYCHAT_LIST("spychat.list"),
	CMD_TKCHAT_RELOADCONFIG("reload.config"),
	FUNC_AUTO_ADD_MODO_LIST("chat.modo"),
	FUNC_CHAT_COLOR("chat.color");

	private String name = "";

	Permissions(String name)
	{
		String prefix = "tkchat.";
		this.name = prefix + name;
	}

	public String toString() {
		return this.name;
	}

	public static int size() {
		return values().length;
	}

}
