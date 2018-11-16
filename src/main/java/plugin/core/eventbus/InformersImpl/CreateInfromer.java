package plugin.core.eventbus.InformersImpl;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import org.jetbrains.annotations.NotNull;
import com.saffrontech.vertx.EventBusBridge;
import plugin.core.eventbus.Informers.ICreateInformer;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

import static plugin.core.util.Address.*;

public class CreateInfromer extends BaseInformer implements ICreateInformer {

    public CreateInfromer(@NotNull String configuration,
                          @NotNull MultiMap headers) {
        super(configuration, headers);
    }

    private void get(@NotNull final String url,
                     @NotNull final JsonObject message) {
        CountDownLatch latch = new CountDownLatch(1);
        EventBusBridge.connect(URI.create(urlEventbus), headers, eb -> eb.send(url, message, reply -> {
            String json = String.valueOf(reply.body());
            System.out.println(json);
            eb.close();
            latch.countDown();
        }));
        awaitLatch(latch);
    }

    // FIXME: don't work
    public void createSubmission() {
        JsonObject message = new JsonObject()
                .put("filter", "state in [\"pending\"]")
                .put("find", new JsonObject().put("project_id", "524"))
                .put("limit", 2)
                .put("offset", 0)
                .put("table", "submission");
        get(URL_EVENTBUS_CREATE_SUBMISSION, message);
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
        get(URL_EVENTBUS_CREATE_PROJECT, message);
    }


    // FIXME don't work
    public void createComment() {
        JsonObject message = new JsonObject()
                .put("authorId", "446")
                .put("datetime", "")
                .put("id", "")
                .put("originalSubmissionId", "")
                .put("persistentCommentId", "")
                .put("previousCommentId", "")
                .put("sourcefile", "")
                .put("sourceline", "")
                .put("state", "")
                .put("submissionId", "")
                .put("text", "");
        get(URL_EVENTBUS_CREATE_COMMENT, message);
    }

}
