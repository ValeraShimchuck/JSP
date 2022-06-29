package ua.valeriishymchuk.jsp.validator;


import ua.valeriishymchuk.jsp.interfaces.validator.IValidator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class SHA256Validator implements IValidator {

    private final String data;
    private final String walletToken;

    private SHA256Validator(String content, String walletToken) {
        this.data = content;
        this.walletToken = walletToken;
    }

    public static SHA256Validator of(String data, String walletToken) {
        return new SHA256Validator(data, walletToken);
    }

    @Override
    public boolean validate(String header) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(walletToken.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256HMAC.init(secretKey);
            String hash = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
            return header.equals(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
