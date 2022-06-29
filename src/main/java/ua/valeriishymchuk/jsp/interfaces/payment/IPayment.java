package ua.valeriishymchuk.jsp.interfaces.payment;

import ua.valeriishymchuk.jsp.interfaces.validator.IValidator;
import ua.valeriishymchuk.jsp.payment.PaymentData;
import ua.valeriishymchuk.jsp.wallet.WalletKey;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public interface IPayment {

    URI getRedirectURI();
    URI getWebhookURI();
    String getPaymentData();
    int getAmount();

    IValidator getValidator();

    IPayment withAmount(int amount);

    IPayment withPaymentData(String paymentData);

    IPayment withRedirectURI(URI redirectURI);

    IPayment withWebhookURI(URI webhookURI);

    IPayment withWalletKey(WalletKey walletKey);

    CompletableFuture<URI> sendPayment();

}
