package ua.valeriishymchuk.jsp.simplehttp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

import java.nio.charset.StandardCharsets;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class HttpResponseResult {

    @SneakyThrows
    public HttpResponseResult(CloseableHttpResponse response) {
        code = response.getCode();
        reasonPhrase = response.getReasonPhrase();
        content = unpack(response.getEntity().getContent().readAllBytes());
    }

    int code;
    String reasonPhrase;
    JsonObject content;

    private JsonObject unpack(byte[] bytes) {
        return (JsonObject) JsonParser.parseString(new String(bytes, StandardCharsets.UTF_8));
    }

    public void throwIfNotSuccess() throws HTTPException {
        if(isSuccess()) return;
        throw new HTTPException(code + " " + reasonPhrase);
    }

    @SneakyThrows
    public void throwIfNotSuccessSneaky() {
        throwIfNotSuccess();
    }

    public boolean isSuccess() {
        return (code + "").startsWith("2");
    }
}
