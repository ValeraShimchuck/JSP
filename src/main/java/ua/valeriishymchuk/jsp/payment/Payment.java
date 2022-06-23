package ua.valeriishymchuk.jsp.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.hc.core5.http.Method;
import ua.valeriishymchuk.jsp.values.ApiValues;
import ua.valeriishymchuk.jsp.interfaces.payment.IPayment;
import ua.valeriishymchuk.jsp.simplehttp.SimpleHTTP;
import ua.valeriishymchuk.jsp.wallet.WalletKey;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Payment implements IPayment {

    int amount;
    URI redirectURI;
    URI webhookURI;
    String paymentData;

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
}
