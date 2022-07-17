package ua.valeriishymchuk.jsp.values;

import java.net.URI;
import java.net.URISyntaxException;

public final class ApiValues {

    public static final String API_URL = "https://spworlds.ru/api/public/";

    private ApiValues() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static enum Operations {
        SEND_MONEY("transactions"),
        SEND_PAYMENT_REQUEST("payment"),
        GET_USER("users/"),
        CARD("card");
        private final String operation;

        private Operations(String operation) {
            this.operation = operation;
        }

        public URI getUrl() {
            try {
                return new URI(API_URL + operation);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
