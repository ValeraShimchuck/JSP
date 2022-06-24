package ua.valeriishymchuk.jsp.wallet;

import java.util.Base64;
import java.util.UUID;

public final class WalletKey {

    private final String key;

    public WalletKey(String walletId, String walletToken) {
        if(!walletId.matches("[a-f\\d]{8}(?:-[a-f\\d]{4}){4}[a-f\\d]{8}"))
            throw new RuntimeException("invalid wallet id");
        if(!walletToken.matches("[a-zA-Z\\d/+]{32}")) throw new RuntimeException("invalid wallet token");
        this.key = walletId + ":" + walletToken;
    }

    public WalletKey(String key) {
        this(key.split(":")[0], key.split(":")[1]);
    }

    public WalletKey(UUID walletID, String walletToken) {
        this(walletID.toString(), walletToken);
    }

    public String getAuthorizationHeader() {
        return "Bearer " + Base64.getEncoder().encodeToString(key.getBytes());
    }

    public String getKey() {
        return key;
    }
}
