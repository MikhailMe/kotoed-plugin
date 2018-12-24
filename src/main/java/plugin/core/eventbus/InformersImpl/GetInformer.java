package plugin.core.eventbus.InformersImpl;

import io.vertx.core.MultiMap;
import plugin.core.course.Course;
import plugin.core.project.Project;
import plugin.core.comment.Comment;
import plugin.core.parser.GetParser;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import plugin.core.sumbission.Submission;
import org.jetbrains.annotations.NotNull;
import com.saffrontech.vertx.EventBusBridge;
import plugin.core.eventbus.Informers.IGetInformer;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CompletableFuture;

import static plugin.core.util.Address.*;

public class GetInformer extends BaseInformer implements IGetInformer {

    public GetInformer(@NotNull String configuration,
                       @NotNull MultiMap headers) {
        super(configuration, headers);
    }

    private <T> List<T> get(@NotNull final String url,
                            @NotNull final JsonObject message,
                            @NotNull final Function<String, List<T>> parse) {
        CountDownLatch latch = new CountDownLatch(1);
        final List<T> list = new ArrayList<>();
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> eb.send(url, message, reply -> {
            String json = String.valueOf(reply.body());
            CompletableFuture<List<T>> cf = new CompletableFuture<>();
            //noinspection unchecked
            cf.complete(Objects.requireNonNull(parse.apply(json)));
            list.addAll(cf.getNow(null));
            eb.close();
            latch.countDown();
        }));
        awaitLatch(latch);
        return list;
    }

    @NotNull
    public List<Course> getCourses() {
        JsonObject message = new JsonObject().put(FIELD_TEXT, EMPTY_STRING);
        return get(URL_EVENTBUS_COURSES, message, GetParser::parseCourses);
    }

    @NotNull
    public List<Project> getProjects(final long courseId,
                                     final long pageSize,
                                     final long currentPage) {
        JsonObject message = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject().put(FIELD_COURSE_ID, courseId));
        return get(URL_EVENTBUS_PROJECTS, message, GetParser::parseProjects);
    }

    @NotNull
    public List<Submission> getSubmissions(final long projectId,
                                           final long pageSize,
                                           final long currentPage) {
        JsonObject message = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject()
                        .put(FIELD_PROJECT_ID, projectId)
                        .put(STATE_IN, new JsonArray().add(OPEN).add(CLOSED)));
        return get(URL_EVENTBUS_SUBMISSIONS, message, GetParser::parseSubmissions);
    }

    @NotNull
    public List<Comment> getComments(final long submissionNumber) {
        JsonObject message = new JsonObject().put(FIELD_ID, submissionNumber);
        return get(URL_EVENTBUS_COMMENTS, message, GetParser::parseComments);
    }

}
