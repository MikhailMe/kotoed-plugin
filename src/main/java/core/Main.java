package core;

import com.saffrontech.vertx.EventBusBridge;
import core.comment.Comment;
import core.parser.Parser;
import core.sumbission.Submission;
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
import java.util.List;
import java.util.Map;

import static core.Address.*;

public class Main {

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

        String stringOfHeaders = Arrays.toString(response.getAllHeaders());
        String cookie = Parser.getCookieFromHeaders(stringOfHeaders);

        MultiMap headers = MultiMap.caseInsensitiveMultiMap();
        headers.add("Cookie", cookie);

        EventBusBridge.connect(URI.create(URL_LOCAL_EVENTBUS), headers, eb -> {

            int submissionNumber = 7672;
            JsonObject submission = new JsonObject().put("id", submissionNumber);
            eb.send(URL_EVENTBUS_COMMENTS, submission, reply -> {
                String comments = String.valueOf(reply.body());

                List<Comment> commentsOfSubmission = Parser.getCommnets(comments);

                // number of submission, comment list for this submission
                Map<Integer, List<Comment>> allCommnets;

            });


            JsonObject submissionList = new JsonObject().put("TODO", "TODO");
            eb.send(URL_EVENTBUS_SUBMISSIONS, submissionList, reply -> {
                String submissions = String.valueOf(reply.body());
                List<Submission> submissionsOfProject = Parser.getSubmissions(submissions);

            });
        });
    }

}
