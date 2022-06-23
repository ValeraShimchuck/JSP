package ua.valeriishymchuk.jsp.wallet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.hc.core5.http.Method;
import ua.valeriishymchuk.jsp.values.ApiValues;
import ua.valeriishymchuk.jsp.interfaces.wallet.IWallet;
import ua.valeriishymchuk.jsp.simplehttp.HttpResponseResult;
import ua.valeriishymchuk.jsp.simplehttp.SimpleHTTP;

import java.util.concurrent.CompletableFuture;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Wallet implements IWallet {

    WalletKey key;

    public Wallet(String walletID, String walletToken) {
        this(new WalletKey(walletID, walletToken));
    }

    @Override
    public CompletableFuture<HttpResponseResult> sendMoney(WalletNumber walletNumber, int amount, String comment) {
        if (amount <= 0) throw new RuntimeException("amount must be positive");
        return SimpleHTTP.jsonApplication(ApiValues.Operations.SEND_MONEY.getUrl())
                .addHeader("Authorization", key.getAuthorizationHeader())
                .addContent("receiver", walletNumber.toString())
                .addContent("amount", amount)
                .addContent("comment", comment)
                .send(Method.POST);
    }
}
