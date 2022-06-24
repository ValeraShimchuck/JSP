package ua.valeriishymchuk.jsp.simplehttp;

import com.google.common.io.ByteStreams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class HttpResponseResult {

    public HttpResponseResult(CloseableHttpResponse response) {
        code = response.getCode();
        reasonPhrase = response.getReasonPhrase();
        try {
            content = unpack(readAllBytes(response.getEntity().getContent()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readAllBytes(InputStream stream) throws IOException {
        return ByteStreams.toByteArray(stream);
    }

    private final int code;
    private final String reasonPhrase;
    private final JsonObject content;

    public HttpResponseResult(int code, String reasonPhrase, JsonObject content) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
        this.content = content;
    }

    private JsonObject unpack(byte[] bytes) {
        JsonElement element = JsonParser.parseString(new String(bytes, StandardCharsets.UTF_8));
        if(element.isJsonNull()) return null;
        if(!element.isJsonObject()) {
            throw new RuntimeException(element.getClass() + " unhandled");
        }
        return (JsonObject) element;
    }

    public void throwIfNotSuccess() throws HTTPException {
        if(isSuccess()) return;
        throw new HTTPException(code + " " + reasonPhrase);
    }


    public void throwIfNotSuccessSneaky() {
        try {
            throwIfNotSuccess();
        } catch (HTTPException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSuccess() {
        return (code + "").startsWith("2");
    }

    public int getCode() {
        return this.code;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public JsonObject getContent() {
        return this.content;
    }
}
