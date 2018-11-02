package plugin.core.rest;

import io.vertx.core.MultiMap;
import plugin.core.parser.Parser;
import io.vertx.core.json.JsonObject;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static plugin.core.Util.Address.*;

public class Sender {

    @NotNull
    private final HttpClient client;

    @Getter
    private String jsonMyself;

    private String URL_SIGN_IN;
    private String URL_SIGN_UP;
    private String URL_WHO_AM_I;

    private static final String LOCAL = "LOCAL";
    private static final String GLOBAL = "GLOBAL";
    private static final String FIELD_COOKIE = "Cookie";

    public Sender(@NotNull final String configuration) {

        this.client = HttpClientBuilder.create().build();

        if (configuration.equals(GLOBAL)) {
            URL_SIGN_IN = URL_GLOBAL_SIGN_IN;
            URL_SIGN_UP = URL_GLOBAL_SIGN_UP;
            URL_WHO_AM_I = URL_GLOBAL_WHO_AM_I;
        }
        if (configuration.equals(LOCAL)) {
            URL_SIGN_IN = URL_LOCAL_SIGN_IN;
            URL_SIGN_UP = URL_LOCAL_SIGN_UP;
            URL_WHO_AM_I = URL_LOCAL_WHO_AM_I;
        }
    }

    public String signIn(@NotNull final String denizen,
                         @NotNull final String password) throws IOException {
        this.jsonMyself = new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
        return getCookie(jsonMyself);
    }

    private String getCookie(@NotNull final String jsonMyself) throws IOException {
        HttpPost postForCookies = new HttpPost(URL_SIGN_IN);
        postForCookies.setEntity(new StringEntity(jsonMyself));
        HttpResponse responseWithCookies = client.execute(postForCookies);
        String stringOfHeaders = Arrays.toString(responseWithCookies.getAllHeaders());
        return Parser.getCookieFromHeaders(stringOfHeaders);
    }

    public String getWhoAmI() throws IOException {
        return getWhoAmI(jsonMyself);
    }

    private String getWhoAmI(@NotNull final String jsonMyself) throws IOException {
        HttpPost postWhoAmI = new HttpPost(URL_WHO_AM_I);
        postWhoAmI.setEntity(new StringEntity(jsonMyself));
        HttpResponse whoAmI = client.execute(postWhoAmI);
        BufferedReader rd = getReader(whoAmI);
        return IOUtils.toString(rd);
    }

    private BufferedReader getReader(@NotNull final HttpResponse response) throws IOException {
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }

    public void signUp(@NotNull final String denizen,
                       @NotNull final String password) throws IOException {
        this.jsonMyself = new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
        HttpPost postForCookies = new HttpPost(URL_SIGN_UP);
        postForCookies.setEntity(new StringEntity(jsonMyself));
        client.execute(postForCookies);
    }

    public MultiMap getHeaders(String cookie) {
        return MultiMap.caseInsensitiveMultiMap().add(FIELD_COOKIE, cookie);
    }

}
