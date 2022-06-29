package ua.valeriishymchuk.jsp.payment;

import ua.valeriishymchuk.jsp.validator.SHA256Validator;

import java.net.URI;

public class PaymentData {

    private final SHA256Validator validator;
    private final URI toSendUserURI;

    public PaymentData(SHA256Validator validator, URI toSendUserURI) {
        this.validator = validator;
        this.toSendUserURI = toSendUserURI;
    }

    public SHA256Validator getValidator() {
        return validator;
    }

    public URI getToSendUserURI() {
        return toSendUserURI;
    }
}
