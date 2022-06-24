package ua.valeriishymchuk.jsp.payment;

import org.apache.hc.core5.http.Method;
import ua.valeriishymchuk.jsp.interfaces.payment.IPayment;
import ua.valeriishymchuk.jsp.simplehttp.SimpleHTTP;
import ua.valeriishymchuk.jsp.values.ApiValues;
import ua.valeriishymchuk.jsp.wallet.WalletKey;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class Payment implements IPayment {

    private final int amount;
    private final URI redirectURI;
    private final URI webhookURI;
    private final String paymentData;

    public Payment(int amount, URI redirectURI, URI webhookURI, String paymentData) {
        this.amount = amount;
        this.redirectURI = redirectURI;
        this.webhookURI = webhookURI;
        this.paymentData = paymentData;
    }

    @Override
    public CompletableFuture<URI> sendPayment(WalletKey key) {
        return new SimpleHTTP(ApiValues.Operations.SEND_PAYMENT_REQUEST.getUrl())
                .addHeader("Authorization", key.getAuthorizationHeader())
                .addContent("amount", amount)
                .addContent("redirectUrl", redirectURI.toString())
                .addContent("webhookUrl", webhookURI.toString())
                .addContent("data", paymentData)
                .send(Method.POST)
                .thenApply(httpResponseResult -> {
                    httpResponseResult.throwIfNotSuccessSneaky();
                    return URI.create(httpResponseResult.getContent().get("url").getAsString());
                });
    }

    public int getAmount() {
        return this.amount;
    }

    public URI getRedirectURI() {
        return this.redirectURI;
    }

    public URI getWebhookURI() {
        return this.webhookURI;
    }

    public String getPaymentData() {
        return this.paymentData;
    }
}
