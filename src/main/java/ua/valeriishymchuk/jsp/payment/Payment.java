package ua.valeriishymchuk.jsp.payment;

import com.google.gson.JsonObject;
import org.apache.hc.core5.http.Method;
import ua.valeriishymchuk.jsp.interfaces.payment.IPayment;
import ua.valeriishymchuk.jsp.simplehttp.SimpleHTTP;
import ua.valeriishymchuk.jsp.validator.SHA256Validator;
import ua.valeriishymchuk.jsp.values.ApiValues;
import ua.valeriishymchuk.jsp.wallet.WalletKey;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class Payment implements IPayment {

    private final int amount;
    private final URI redirectURI;
    private final URI webhookURI;
    private final String paymentData;
    private final WalletKey walletKey;

    public Payment(int amount, URI redirectURI, URI webhookURI, String paymentData, WalletKey walletKey) {
        this.amount = amount;
        this.redirectURI = redirectURI;
        this.webhookURI = webhookURI;
        this.paymentData = paymentData;
        this.walletKey = walletKey;
    }

    @Override
    public CompletableFuture<URI> sendPayment() {
        return new SimpleHTTP(ApiValues.Operations.SEND_PAYMENT_REQUEST.getUrl())
                .addHeader("Authorization", walletKey.getAuthorizationHeader())
                .setJSONContent(getContent())
                .send(Method.POST)
                .thenApply(httpResponseResult -> {
                    httpResponseResult.throwIfNotSuccessSneaky();
                    return URI.create(httpResponseResult.getContent().get("url").getAsString());
                });
    }

    @Override
    public SHA256Validator getValidator() {
        return SHA256Validator.of(getContent().toString(), walletKey.getWalletToken());
    }

    private JsonObject getContent() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("redirectUrl", redirectURI.toString());
        jsonObject.addProperty("webhookUrl", webhookURI.toString());
        jsonObject.addProperty("data", paymentData);
        return jsonObject;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public IPayment withAmount(int amount) {
        return new Payment(amount, redirectURI, webhookURI, paymentData, walletKey);
    }

    @Override
    public IPayment withPaymentData(String paymentData) {
        return new Payment(amount, redirectURI, webhookURI, paymentData, walletKey);
    }

    @Override
    public IPayment withRedirectURI(URI redirectURI) {
        return new Payment(amount, redirectURI, webhookURI, paymentData, walletKey);
    }

    @Override
    public IPayment withWebhookURI(URI webhookURI) {
        return new Payment(amount, redirectURI, webhookURI, paymentData, walletKey);
    }

    @Override
    public IPayment withWalletKey(WalletKey walletKey) {
        return new Payment(amount, redirectURI, webhookURI, paymentData, walletKey);
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
