package plugin.core.rest;

import lombok.Getter;
import io.vertx.core.MultiMap;
import plugin.core.parser.Parser;
import org.apache.http.HttpResponse;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import static plugin.core.util.Address.*;

public class Sender {

    private String urlSignIn;
    private String urlSignUp;
    private String urlWhoAmI;

    @Getter
    private String jsonMyself;

    @NotNull
    private final HttpClient client;

    private static final String FIELD_COOKIE = "Cookie";

    public Sender(@NotNull final String configuration) {
        this.client = HttpClientBuilder.create().build();

        if (configuration.equals(GLOBAL)) {
            urlSignIn = URL_GLOBAL_SIGN_IN;
            urlSignUp = URL_GLOBAL_SIGN_UP;
            urlWhoAmI = URL_GLOBAL_WHO_AM_I;
        }
        if (configuration.equals(LOCAL)) {
            urlSignIn = URL_LOCAL_SIGN_IN;
            urlSignUp = URL_LOCAL_SIGN_UP;
            urlWhoAmI = URL_LOCAL_WHO_AM_I;
        }
    }

    @NotNull
    public String signIn(@NotNull final String denizen,
                         @NotNull final String password) {
        this.jsonMyself = new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
        return Objects.requireNonNull(getCookie(jsonMyself));
    }

    private String getCookie(@NotNull final String jsonMyself) {
        try {
            HttpPost postForCookies = new HttpPost(urlSignIn);
            postForCookies.setEntity(new StringEntity(jsonMyself));
            HttpResponse responseWithCookies = client.execute(postForCookies);
            String stringOfHeaders = Arrays.toString(responseWithCookies.getAllHeaders());
            return Parser.getCookieFromHeaders(stringOfHeaders);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getWhoAmI() {
        return jsonMyself == null ? null : Objects.requireNonNull(getWhoAmI(jsonMyself));
    }

    private String getWhoAmI(@NotNull final String jsonMyself) {
        try {
            HttpPost postWhoAmI = new HttpPost(urlWhoAmI);
            postWhoAmI.setEntity(new StringEntity(jsonMyself));
            HttpResponse whoAmI = client.execute(postWhoAmI);
            BufferedReader rd = getReader(whoAmI);
            return IOUtils.toString(rd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    private BufferedReader getReader(@NotNull final HttpResponse response) throws IOException {
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }

    public void signUp(@NotNull final String denizen,
                       @NotNull final String password) {
        this.jsonMyself = new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
        try {
            HttpPost postForCookies = new HttpPost(urlSignUp);
            postForCookies.setEntity(new StringEntity(jsonMyself));
            client.execute(postForCookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public MultiMap getHeaders(String cookie) {
        return MultiMap.caseInsensitiveMultiMap().add(FIELD_COOKIE, cookie);
    }
}
