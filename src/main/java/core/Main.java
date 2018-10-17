package core;

import com.saffrontech.vertx.EventBusBridge;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;

import static core.Address.*;

public class Main {

    private static String getCookieFromHeaders(final String headers) {
        String prefix = "Set-Cookie: ";
        String postfix = ";";
        int beginIndex = headers.indexOf(prefix) + prefix.length();
        int endIndex = headers.indexOf(postfix);
        return headers.substring(beginIndex, endIndex);
    }

    public static void main(String[] args) throws IOException {

        String localDeneziedId = "admin";
        String localPassword = "adminadmin";


        String stringRequest = new JsonObject()
                .put(DENIZEN_ID, localDeneziedId)
                .put(PASSWORD, localPassword)
                .toString();

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL_LOCAL_LOGIN);
        StringEntity input = new StringEntity(stringRequest);
        post.setEntity(input);
        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }

        String cookie = getCookieFromHeaders(Arrays.toString(response.getAllHeaders()));
        System.out.println(cookie);


        MultiMap headers = MultiMap.caseInsensitiveMultiMap();
        headers.add("Cookie", cookie);

        EventBusBridge.connect(URI.create(URL_LOCAL_EVENTBUS), headers, eb -> {
            eb.send(URL_EVENTBUS_COMMENTS, new JsonObject().put("id", 7672), reply -> {
                System.out.println(reply.body());
            });
        });
    }

}
