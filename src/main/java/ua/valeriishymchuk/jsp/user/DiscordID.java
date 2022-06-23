package ua.valeriishymchuk.jsp.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class DiscordID {

    String discordID;

    public DiscordID(String discordID) {
        if(!discordID.matches("\\d{18}")) throw new RuntimeException("invalid discord id");
        this.discordID = discordID;
    }

}
