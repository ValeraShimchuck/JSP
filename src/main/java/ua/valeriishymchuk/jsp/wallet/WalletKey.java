package ua.valeriishymchuk.jsp.wallet;

import ua.valeriishymchuk.jsp.simplehttp.SimpleHTTP;
import ua.valeriishymchuk.jsp.values.ApiValues;

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

    public String getWalletToken() {
        return key.split(":")[1];
    }

    public String getWalletId() {
        return key.split(":")[0];
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

    public SimpleHTTP getRequestBuilder(ApiValues.Operations operation) {
        return SimpleHTTP.jsonApplication(operation.getUrl())
                .addHeader("Authorization", getAuthorizationHeader());
    }
}
