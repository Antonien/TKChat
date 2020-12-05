package fr.talyoki.tkchat.data;

public enum MsgScope {
    GLOBAL("GLOBAL"),
    SERVER("SERVER");

    private String name = "";

    MsgScope(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static int size() {
        return values().length;
    }
}
