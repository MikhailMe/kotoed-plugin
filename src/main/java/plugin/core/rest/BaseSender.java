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

    @Getter
    String cookie;

    @NotNull
    private final HttpClient client;

    final String FIELD_COOKIE = "Cookie";

    BaseSender(@NotNull final String configuration) {
        this.cookie = "";
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
                      @NotNull final String json,
                      @NotNull final String cookie) {
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(json));
            if (!cookie.isEmpty()) {
                post.setHeader(FIELD_COOKIE, cookie);
            }
            return client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String setCookie() {
        HttpResponse responseWithCookies = post(urlSignIn, jsonMyself, cookie);
        String stringOfHeaders = Arrays.toString(responseWithCookies.getAllHeaders());
        cookie = GetParser.parseCookieFromHeaders(stringOfHeaders);
        return cookie;
    }

    @NotNull
    BufferedReader getReader(@NotNull final HttpResponse response) throws IOException {
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }
}
