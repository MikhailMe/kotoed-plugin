package core;

import core.comment.Comment;
import core.course.Course;
import core.parser.Parser;
import core.sumbission.Submission;
import io.vertx.core.MultiMap;
import org.apache.http.HttpResponse;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import com.saffrontech.vertx.EventBusBridge;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static core.Address.*;

public class Main {

    private static final String FIELD_ID = "id";
    private static final String FIELD_FIND = "find";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_COOKIE = "Cookie";
    private static final String FIELD_PAGE_SIZE = "page_size";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_CURRENT_PAGE = "current_page";

    private static List<Course> courses;
    private static List<Comment> commentsForSubmission;
    private static List<Submission> submissionsForCourse;

    private static String getMyself(@NotNull final String denizen,
                                    @NotNull final String password) {
        return new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
    }

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

    // TODO: 10/25/2018 Think about: "how use courses outside scope this method"
    private static void getCourses(@NotNull final EventBusBridge eb) {
        JsonObject message = new JsonObject().put(FIELD_TEXT, "");
        eb.send(URL_EVENTBUS_COURSES, message, reply -> {
            String jsonCourses = String.valueOf(reply.body());
            
            try {
                courses = Parser.getCourses(jsonCourses);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Course course : courses) {
                System.out.println(course.toString());
            }

        });
    }

    private static void getComments(@NotNull final EventBusBridge eb,
                                    final int submissionNumber) {
        JsonObject message = new JsonObject().put(FIELD_ID, submissionNumber);
        eb.send(URL_EVENTBUS_COMMENTS, message, reply -> {
            String jsonComments = String.valueOf(reply.body());
            System.out.println(jsonComments);
            //commentsForSubmission = Parser.getComments(jsonComments);
        });
    }

    private static void getSubmissions(@NotNull final EventBusBridge eb,
                                       final int courseId) {
        int pageSize = 20;
        int currentPage = 0;
        JsonObject message = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject().put(FIELD_COURSE_ID, courseId));
        eb.send(URL_EVENTBUS_SUBMISSIONS_FOR_COURSE, message, reply -> {
            String jsonSubmissions = String.valueOf(reply.body());
            System.out.println(jsonSubmissions);
            //submissionsForCourse = Parser.getSubmissions(jsonSubmissions);
        });
    }

    public static void main(String[] args) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        // This is a local example that is added by my hands
        String localDenizen = "punsh";
        String localPassword = "punsh";
        String jsonMyself = getMyself(localDenizen, localPassword);

        String cookie = getCookie(URL_LOCAL_LOGIN, client, jsonMyself);
        String whoAmI = getWhoAmI(URL_LOCAL_WHO_AM_I, client, jsonMyself);

        MultiMap headers = MultiMap.caseInsensitiveMultiMap().add(FIELD_COOKIE, cookie);

        EventBusBridge.connect(URI.create(URL_LOCAL_EVENTBUS), headers, eb -> {

            // all courses in Kotoed
            //getCourses(eb);

            // all submissions of course with id == 8
            //getSubmissions(eb, 8);

            // all comments of submission with id == 8608
            //getComments(eb, 8608);

        });

    }

}
