package plugin.core.eventbus;

import io.vertx.core.MultiMap;
import plugin.core.course.Course;
import plugin.core.parser.Parser;
import plugin.core.project.Project;
import plugin.core.comment.Comment;
import io.vertx.core.json.JsonObject;
import plugin.core.sumbission.Submission;
import org.jetbrains.annotations.NotNull;
import com.saffrontech.vertx.EventBusBridge;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CompletableFuture;

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
    public List<Course> getCourses() {
        JsonObject message = new JsonObject().put(FIELD_TEXT, EMPTY_STRING);
        CountDownLatch latch = new CountDownLatch(1);
        final List<Course> courseList = new ArrayList<>();
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_COURSES, message, reply -> {
                String jsonCourses = String.valueOf(reply.body());
                CompletableFuture<List<Course>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getCourses(jsonCourses)));
                courseList.addAll(cf.getNow(null));
                eb.close();
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return courseList;
    }

    // FIXME
    @NotNull
    public List<Project> getProjects() {
        JsonObject message = new JsonObject().put(FIELD_ID, EMPTY_STRING);
        CountDownLatch latch = new CountDownLatch(1);
        final List<Project> projectList = new ArrayList<>();
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            // FIXME: 11/3/2018 change URL_TO_EVENTBUS
            eb.send("URL_TO_EVENTBUS", message, reply -> {
                String jsonProjects = String.valueOf(reply.body());
                CompletableFuture<List<Project>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getProjects(jsonProjects)));
                projectList.addAll(cf.getNow(null));
                eb.close();
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return projectList;
    }

    @NotNull
    public List<Submission> getSubmissions(final int courseId,
                                           final int pageSize,
                                           final int currentPage) {
        JsonObject message = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject().put(FIELD_COURSE_ID, courseId));
        CountDownLatch latch = new CountDownLatch(1);
        final List<Submission> submissionList = new ArrayList<>();
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_SUBMISSIONS_FOR_COURSE, message, reply -> {
                String jsonSubmissions = String.valueOf(reply.body());
                CompletableFuture<List<Submission>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getSubmissions(jsonSubmissions)));
                submissionList.addAll(cf.getNow(null));
                eb.close();
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return submissionList;
    }

    @NotNull
    public List<Comment> getComments(final int submissionNumber) {
        JsonObject message = new JsonObject().put(FIELD_ID, submissionNumber);
        CountDownLatch latch = new CountDownLatch(1);
        final List<Comment> commentsList = new ArrayList<>();
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_COMMENTS, message, reply -> {
                String jsonComments = String.valueOf(reply.body());
                CompletableFuture<List<Comment>> cf = new CompletableFuture<>();
                cf.complete(Objects.requireNonNull(Parser.getComments(jsonComments)));
                commentsList.addAll(cf.getNow(null));
                eb.close();
                latch.countDown();
            });
        });
        awaitLatch(latch);
        return commentsList;
    }

    // FIXME: don't work
    public void createSubmission() {
        JsonObject message = new JsonObject()
                .put("filter", "state in [\"pending\"]")
                .put("find", new JsonObject().put("project_id", "524"))
                .put("limit", 2)
                .put("offset", 0)
                .put("table", "submission");
        CountDownLatch latch = new CountDownLatch(1);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_CREATE_SUBMISSION, message, reply -> {
                String jsonCreateSubmission = String.valueOf(reply.body());

                System.out.println(jsonCreateSubmission);
                eb.close();
                latch.countDown();
            });
        });
        awaitLatch(latch);
    }

    // FIXME: don't work
    public void createProject() {
        JsonObject message = new JsonObject()
                .put("courseId", 8)
                .put("deleted", false)
                .put(DENIZEN_ID, 446)
                .put("id", "")
                .put("name", "punsh project")
                .put("repoType", "git")
                .put("repoUrl", "https://github.com/belyaev-mikhail/fp-practice-2018");
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_CREATE_PROJECT, message, reply -> {
                String jsonRead = String.valueOf(reply.body());
                System.out.println(jsonRead);
            });
        });
    }

    public void readProject(final int projectId) {
        JsonObject message = new JsonObject().put(FIELD_ID, projectId);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_READ_PROJECT, message, reply -> {
                String jsonRead = String.valueOf(reply.body());
                System.out.println(jsonRead);
                eb.close();
            });
        });
    }

    public void countProjects() {
        JsonObject message = new JsonObject().put(FIELD_TEXT, EMPTY_STRING);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> {
            eb.send(URL_EVENTBUS_COUNT_PROJECTS, message, reply -> {
                String jsonRead = String.valueOf(reply.body());
                System.out.println(jsonRead);
            });
        });
    }

}
