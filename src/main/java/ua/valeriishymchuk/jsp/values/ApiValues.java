package ua.valeriishymchuk.jsp.values;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.net.URI;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@UtilityClass
public class ApiValues {

    String API_URL = "https://spworlds.ru/api/public/";

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @RequiredArgsConstructor
    public enum Operations {
        SEND_MONEY("transactions"),
        SEND_PAYMENT_REQUEST("payment"),
        GET_USER("users/");
        String operation;
        @SneakyThrows
        public URI getUrl() {
            return new URI(API_URL + operation);
        }
    }

}
