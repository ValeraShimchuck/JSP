package ua.valeriishymchuk.jsp.validator;


import ua.valeriishymchuk.jsp.interfaces.validator.IValidator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class SHA256Validator implements IValidator {

    private final String header;
    private final String data;
    private final String walletToken;

    private SHA256Validator(String header, String data, String walletToken) {
        this.header = header;
        this.data = data;
        this.walletToken = walletToken;
    }

    public static SHA256Validator of(String header, String data, String walletToken) {
        return new SHA256Validator(header, data, walletToken);
    }

    @Override
    public boolean validate() {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(walletToken.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String hash = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
            return header.equals(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
