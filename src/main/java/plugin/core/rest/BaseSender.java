package plugin.core.rest;

import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;
import plugin.core.parser.GetParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static plugin.core.util.Address.*;

public class BaseSender {

    String urlSignIn;
    String urlSignUp;
    String urlWhoAmI;

    @Getter
    String jsonMyself;

    @NotNull
    private final HttpClient client;

    final String FIELD_COOKIE = "Cookie";

    BaseSender(@NotNull final String configuration) {
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

    HttpResponse post(@NotNull final String url,
                      @NotNull final String json) {
        try {
            HttpPost postForCookies = new HttpPost(url);
            postForCookies.setEntity(new StringEntity(json));
            return client.execute(postForCookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String getCookie(@NotNull final String jsonMyself) {
        HttpResponse responseWithCookies = post(urlSignIn, jsonMyself);
        String stringOfHeaders = Arrays.toString(responseWithCookies.getAllHeaders());
        return GetParser.parseCookieFromHeaders(stringOfHeaders);
    }

    @NotNull
    BufferedReader getReader(@NotNull final HttpResponse response) throws IOException {
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }
}
