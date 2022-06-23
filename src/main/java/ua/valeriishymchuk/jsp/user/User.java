package ua.valeriishymchuk.jsp.user;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.hc.core5.http.Method;
import ua.valeriishymchuk.jsp.values.ApiValues;
import ua.valeriishymchuk.jsp.interfaces.user.IUser;
import ua.valeriishymchuk.jsp.simplehttp.SimpleHTTP;
import ua.valeriishymchuk.jsp.wallet.WalletKey;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class User implements IUser {

    String name;

    public static CompletableFuture<Optional<IUser>> getUser(WalletKey key, DiscordID discordID) {
        return SimpleHTTP.jsonApplication(ApiValues.Operations.GET_USER.getUrl().resolve(discordID.getDiscordID()))
                .addHeader("Authorization", key.getAuthorizationHeader())
                .send(Method.GET)
                .thenApply(httpResponseResult -> {
                    if(httpResponseResult.getCode() != 404) httpResponseResult.throwIfNotSuccessSneaky();
                    JsonObject content = httpResponseResult.getContent();
                    if(httpResponseResult.getCode() == 404 || content.get("username").isJsonNull()) return Optional.empty();
                    return Optional.of(new User(content.get("username").getAsString()));
                });
    }

    public static CompletableFuture<Optional<IUser>> getUser(String key, DiscordID discordID) {
        return getUser(new WalletKey(key), discordID);
    }

    public static CompletableFuture<Optional<IUser>> getUser(String walletID, String walletToken, DiscordID discordID) {
        return getUser(new WalletKey(walletID, walletToken), discordID);
    }

    public static CompletableFuture<Optional<IUser>> getUser(WalletKey key, String discordID) {
        return getUser(key, new DiscordID(discordID));
    }

    public static CompletableFuture<Optional<IUser>> getUser(String key, String discordID) {
        return getUser(new WalletKey(key), discordID);
    }

    public static CompletableFuture<Optional<IUser>> getUser(String walletID, String walletToken, String discordID) {
        return getUser(new WalletKey(walletID, walletToken), discordID);
    }

}
