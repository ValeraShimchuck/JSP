package ua.valeriishymchuk.jsp.simplehttp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SimpleHTTP {

    private final URI uri;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final Map<String, Object> content;

    public SimpleHTTP(URI uri, Map<String, String> headers, Map<String, String> parameters) {
        this(uri, headers, parameters, new HashMap<>());
    }

    public SimpleHTTP(URI uri, Map<String, String> headers) {
        this(uri, headers, new HashMap<>());
    }

    public SimpleHTTP(URI uri) {
        this(uri, new HashMap<>());
    }

    public SimpleHTTP(URI uri, Map<String, String> headers, Map<String, String> parameters, Map<String, Object> content) {
        this.uri = uri;
        this.headers = headers;
        this.parameters = parameters;
        this.content = content;
    }

    public SimpleHTTP addHeader(String parameter, String value) {
        headers.put(parameter, value);
        return this;
    }

    public SimpleHTTP addParameter(String parameter, String value) {
        parameters.put(parameter, value);
        return this;
    }

    public SimpleHTTP addContent(String parameter, Object obj) {
        if(!isClassMatch(obj,
                Number.class,
                String.class,
                JsonElement.class,
                Character.class,
                Boolean.class)) return this;
        content.put(parameter, obj);
        return this;
    }

    public CompletableFuture<HttpResponseResult> send(Method method) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                try (final CloseableHttpClient httpClient = HttpClients.custom()
                        .setDefaultCookieStore(new BasicCookieStore())
                        .build()) {
                    ClassicHttpRequest request = createRequest(method);
                    try (final CloseableHttpResponse response = httpClient.execute(request)){
                        return new HttpResponseResult(response);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ClassicHttpRequest createRequest(Method method) {
        ClassicRequestBuilder requestBuilder = ClassicRequestBuilder.create(method.name());
        requestBuilder.setUri(uri);
        headers.forEach(requestBuilder::addHeader);
        parameters.forEach(requestBuilder::addParameter);
        requestBuilder.setEntity(pack(content));
        return requestBuilder.build();
    }

    private String pack(Map<String, Object> mapToPack) {
        JsonObject jsonObject = new JsonObject();
        mapToPack.forEach((property, value) -> {
            if(value instanceof Number) jsonObject.addProperty(property, (Number) value);
            else if(value instanceof String) jsonObject.addProperty(property, (String) value);
            else if(value instanceof Boolean) jsonObject.addProperty(property, (Boolean) value);
            else if(value instanceof Character) jsonObject.addProperty(property, (Character) value);
            else if(value instanceof JsonElement) jsonObject.add(property, (JsonElement) value);
            else throw new RuntimeException(value.getClass() + " unhandled");
        });
        return jsonObject.toString();
    }

    private JsonObject unpack(byte[] bytes) {
        return (JsonObject) JsonParser.parseString(new String(bytes, StandardCharsets.UTF_8));
    }

    private boolean isClassMatch(Object obj, Class<?>... classes) {
        return Arrays.stream(classes).anyMatch(clazz -> clazz.isInstance(obj));
    }

    public static SimpleHTTP jsonApplication(URI uri) {
        SimpleHTTP post = new SimpleHTTP(uri);
        post.headers.put("Accept", "application/json");
        post.headers.put("Content-type", "application/json");
        return post;
    }

}
