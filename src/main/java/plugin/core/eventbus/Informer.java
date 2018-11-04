package plugin.core.eventbus;

import com.saffrontech.vertx.EventBusBridge;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.core.course.Course;
import plugin.core.parser.Parser;
import plugin.core.project.Project;
import plugin.core.sumbission.Submission;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static plugin.core.util.Address.*;

public class Informer {

    private static final String FIELD_ID = "id";
    private static final String EMPTY_STRING = "";
    private static final String FIELD_FIND = "find";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_PAGE_SIZE = "page_size";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_CURRENT_PAGE = "current_page";

    private MultiMap headers;
    private String urlEventbus;

    public Informer(@NotNull final String configuration,
                    @NotNull final MultiMap headers) {
        this.headers = headers;

        if (configuration.equals(LOCAL)) {
            urlEventbus = URL_LOCAL_EVENTBUS;
        }
        if (configuration.equals(GLOBAL)) {
            urlEventbus = URL_GLOBAL_EVENTBUS;
        }
    }

    private void awaitLatch(@NotNull CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public List<Course> getCourses(@NotNull final List<Course> courseList) {
        JsonObject message = new JsonObject().put(FIELD_TEXT, EMPTY_STRING);
        CountDownLatch latch = new CountDownLatch(1);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_COURSES, message, reply -> {
                String jsonCourses = String.valueOf(reply.body());
//                System.out.println(jsonCourses);
                CompletableFuture<List<Course>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getCourses(jsonCourses)));
                courseList.addAll(cf.getNow(null));
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return courseList;
    }

    // FIXME: 11/3/2018
    @NotNull
    public List<Project> getProjects(@NotNull final List<Project> projectList) {
        JsonObject message = new JsonObject().put(FIELD_ID, EMPTY_STRING);
        CountDownLatch latch = new CountDownLatch(1);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            // FIXME: 11/3/2018 change URL_TO_EVENTBUS
            eb.send("URL_TO_EVENTBUS", message, reply -> {
                String jsonProjects = String.valueOf(reply.body());
                CompletableFuture<List<Project>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getProjects(jsonProjects)));
                projectList.addAll(cf.getNow(null));
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return projectList;
    }

    @NotNull
    public List<Comment> getComments(final int submissionNumber,
                                     @NotNull final List<Comment> commentsList) {
        JsonObject message = new JsonObject().put(FIELD_ID, submissionNumber);
        CountDownLatch latch = new CountDownLatch(1);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_COMMENTS, message, reply -> {
                String jsonComments = String.valueOf(reply.body());
                CompletableFuture<List<Comment>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getComments(jsonComments)));
                commentsList.addAll(cf.getNow(null));
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return commentsList;
    }

    @NotNull
    public List<Submission> getSubmissions(final int courseId,
                                           final int pageSize,
                                           final int currentPage,
                                           @NotNull final List<Submission> submissionList) {
        JsonObject message = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject().put(FIELD_COURSE_ID, courseId));
        CountDownLatch latch = new CountDownLatch(1);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_SUBMISSIONS_FOR_COURSE, message, reply -> {
                String jsonSubmissions = String.valueOf(reply.body());

                System.out.println(jsonSubmissions);

                CompletableFuture<List<Submission>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getSubmissions(jsonSubmissions)));
                submissionList.addAll(cf.getNow(null));
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return submissionList;
    }
}
