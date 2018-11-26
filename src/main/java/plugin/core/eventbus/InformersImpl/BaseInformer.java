package plugin.core.eventbus.InformersImpl;

import io.vertx.core.MultiMap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CountDownLatch;

import static plugin.core.util.Address.*;

public class BaseInformer {

    final String FIELD_ID = "id";
    final String EMPTY_STRING = "";
    final String FIELD_FIND = "find";
    final String FIELD_TEXT = "text";
    final String FIELD_PAGE_SIZE = "page_size";
    final String FIELD_COURSE_ID = "course_id";
    final String FIELD_CURRENT_PAGE = "current_page";

    MultiMap headers;
    String urlEventbus;

    BaseInformer(@NotNull final String configuration,
                 @NotNull final MultiMap headers) {
        this.headers = headers;

        if (configuration.equals(LOCAL)) {
            urlEventbus = URL_LOCAL_EVENTBUS;
        }
        if (configuration.equals(GLOBAL)) {
            urlEventbus = URL_GLOBAL_EVENTBUS;
        }
    }

    void awaitLatch(@NotNull CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
