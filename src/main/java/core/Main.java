package core;

import com.saffrontech.vertx.EventBusBridge;
import core.parser.Parser;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
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
import java.net.URI;
import java.util.Arrays;

import static core.Address.*;

public class Main {

    private static final String FILED_ID = "id";
    private static final String FIELD_TEXT = "text";
    private static final String FILED_COOKIE = "Cookie";
    private static final String PAGE_SIZE = "page_size";
    private static final String CURRENT_PAGE = "current_page";

    private static BufferedReader getReader(@NotNull final HttpResponse response) throws IOException {
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }

    private static String getCookie(@NotNull final String url,
                                    @NotNull final HttpClient client,
                                    @NotNull final String jsonMyself) throws IOException {
        HttpPost postForCookies = new HttpPost(url);
        postForCookies.setEntity(new StringEntity(jsonMyself));
        HttpResponse responseWithCookies = client.execute(postForCookies);
        String stringOfHeaders = Arrays.toString(responseWithCookies.getAllHeaders());
        return Parser.getCookieFromHeaders(stringOfHeaders);
    }

    private static String getWhoAmI(@NotNull final String url,
                                    @NotNull final HttpClient client,
                                    @NotNull final String jsonMyself) throws IOException {
        HttpPost postWhoAmI = new HttpPost(url);
        postWhoAmI.setEntity(new StringEntity(jsonMyself));
        HttpResponse whoAmI = client.execute(postWhoAmI);
        BufferedReader rd = getReader(whoAmI);
        return IOUtils.toString(rd);
    }

    public static void main(String[] args) throws IOException {

        String localDenizenId = "admin";
        String localPassword = "adminadmin";

        HttpClient client = HttpClientBuilder.create().build();

        String jsonMyself = new JsonObject()
                .put(DENIZEN_ID, localDenizenId)
                .put(PASSWORD, localPassword)
                .toString();

        String cookie = getCookie(URL_LOCAL_LOGIN, client, jsonMyself);
        String whoAmI = getWhoAmI(URL_LOCAL_WHO_AM_I, client, jsonMyself);

        MultiMap headers = MultiMap.caseInsensitiveMultiMap();
        headers.add(FILED_COOKIE, cookie);

        EventBusBridge.connect(URI.create(URL_LOCAL_EVENTBUS), headers, eb -> {

            // got all comments of 8235 submission
            int submissionNumber = 8235;
            JsonObject getComments = new JsonObject().put(FILED_ID, submissionNumber);
            eb.send(URL_EVENTBUS_COMMENTS, getComments, reply -> {
                String comments = String.valueOf(reply.body());
                System.out.println(comments);
            });

            // got first 20 submissions of yourself
            /*int pageSize = 20;
            int currentPage = 0;
            JsonObject getSubmissions = new JsonObject()
                    .put(FIELD_TEXT, "")
                    .put(CURRENT_PAGE, currentPage)
                    .put(PAGE_SIZE, pageSize);
            eb.send(URL_EVENTBUS_SUBMISSIONS, getSubmissions, reply -> {
                String submissions = String.valueOf(reply.body());
                System.out.println(submissions);
            });*/
        });
    }

}
