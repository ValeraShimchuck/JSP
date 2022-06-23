package ua.valeriishymchuk.jsp.interfaces.payment;

import ua.valeriishymchuk.jsp.wallet.WalletKey;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public interface IPayment {

    URI getRedirectURI();
    URI getWebhookURI();
    String getPaymentData();
    int getAmount();

    CompletableFuture<URI> sendPayment(WalletKey key);

    default CompletableFuture<URI> sendPayment(String key) {
        return sendPayment(new WalletKey(key));
    }

    default CompletableFuture<URI> sendPayment(String walletID, String walletToken) {
        return sendPayment(new WalletKey(walletID, walletToken));
    }

}
