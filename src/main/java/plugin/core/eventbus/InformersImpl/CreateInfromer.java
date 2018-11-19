package plugin.core.eventbus.InformersImpl;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import org.jetbrains.annotations.NotNull;
import com.saffrontech.vertx.EventBusBridge;
import plugin.core.eventbus.Informers.ICreateInformer;
import plugin.core.parser.CreateParser;
import plugin.core.util.CommentRecord;
import plugin.core.util.ProjectRecord;
import plugin.core.util.SubmissionRecord;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

import static plugin.core.util.Address.*;

public class CreateInfromer extends BaseInformer implements ICreateInformer {

    public CreateInfromer(@NotNull String configuration,
                          @NotNull MultiMap headers) {
        super(configuration, headers);
    }

    private <T> T get(@NotNull final String url,
                      @NotNull final JsonObject message,
                      @NotNull final Function<String, T> parse) {
        CountDownLatch latch = new CountDownLatch(1);
        List<T> objects = new ArrayList<>();
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> eb.send(url, message, reply -> {
            String json = String.valueOf(reply.body());
            CompletableFuture<T> cf = new CompletableFuture<>();
            //noinspection unchecked
            cf.complete(Objects.requireNonNull(parse.apply(json)));
            objects.add(cf.getNow(null));
            eb.close();
            latch.countDown();
        }));
        awaitLatch(latch);
        return objects.get(0);
    }

    public SubmissionRecord createSubmission(final int projectId,
                                             @NotNull final String revision) {
        JsonObject message = new JsonObject()
                .put(PROJECT_ID, projectId)
                .put(REVISION, revision);
        return get(URL_EVENTBUS_CREATE_SUBMISSION, message, CreateParser::parseCreateSubmission);
    }

    public ProjectRecord createProject(@NotNull final String projectName,
                                       final int denizenId,
                                       final int courseId,
                                       @NotNull final String repoType,
                                       @NotNull final String repoUrl) {
        JsonObject message = new JsonObject()
                .put(NAME, projectName)
                .put(DENIZEN_ID, denizenId)
                .put(COURSE_ID, courseId)
                .put(REPO_TYPE, repoType)
                .put(REPO_URL, repoUrl);
        return get(URL_EVENTBUS_CREATE_PROJECT, message, CreateParser::parseCreateProject);
    }

    // FIXME: 11/19/2018
    public CommentRecord createComment(final int submissionId,
                                       final int authorId,
                                       final int sourceLine,
                                       @NotNull final String sourceFile,
                                       @NotNull final String text) {
        JsonObject message = new JsonObject()
                .put(SUBMISSION_ID, submissionId)
                .put(AUTHOR_ID, authorId)
                .put(SOURCEFILE, sourceFile)
                .put(SOURCELINE, sourceLine)
                .put(TEXT, text);
        return get(URL_EVENTBUS_CREATE_COMMENT, message, CreateParser::parseCreateComment);
    }

}
