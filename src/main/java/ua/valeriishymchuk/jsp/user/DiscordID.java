package ua.valeriishymchuk.jsp.user;

public final class DiscordID {

    private final String discordID;

    public DiscordID(String discordID) {
        if(!discordID.matches("\\d{18}")) throw new RuntimeException("invalid discord id");
        this.discordID = discordID;
    }

    public String getDiscordID() {
        return this.discordID;
    }
}
