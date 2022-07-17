package ua.valeriishymchuk.jsp.wallet;

import com.google.gson.JsonObject;
import org.apache.hc.core5.http.Method;
import ua.valeriishymchuk.jsp.interfaces.wallet.IWalletInfo;
import ua.valeriishymchuk.jsp.values.ApiValues;
import ua.valeriishymchuk.jsp.interfaces.wallet.IWallet;
import ua.valeriishymchuk.jsp.simplehttp.HttpResponseResult;

import java.util.concurrent.CompletableFuture;

public class Wallet implements IWallet {

    private final WalletKey key;

    public Wallet(WalletKey key) {
        this.key = key;
    }

    public Wallet(String walletID, String walletToken) {
        this(new WalletKey(walletID, walletToken));
    }

    @Override
    public CompletableFuture<HttpResponseResult> sendMoney(WalletNumber walletNumber, int amount, String comment) {
        if(comment.length() == 0) throw new RuntimeException("comment can`t be empty");
        if (amount <= 0) throw new RuntimeException("amount must be positive");
        return key.getRequestBuilder(ApiValues.Operations.SEND_MONEY)
                .addContent("receiver", walletNumber.toString())
                .addContent("amount", amount)
                .addContent("comment", comment)
                .send(Method.POST);
    }

    @Override
    public CompletableFuture<IWalletInfo> getWalletInfo() {
        return key.getRequestBuilder(ApiValues.Operations.CARD)
                .send(Method.GET)
                .thenApply(response -> {
                    if (!response.isSuccess()) throw new RuntimeException("invalid walletID/walletToken");
                    JsonObject json = response.getContent();
                    return new WalletInfo(json.get("balance").getAsInt());
                });
    }


}
