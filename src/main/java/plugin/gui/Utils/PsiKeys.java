package plugin.gui.Utils;

import io.vertx.core.MultiMap;
import com.intellij.openapi.util.Key;
import plugin.core.sumbission.Submission;

import java.util.List;

public class PsiKeys {

    public static final Key<String> PSI_KEY_COOKIE = Key.create("Cookie");
    public static final Key<MultiMap> PSI_KEY_HEADERS = Key.create("Headers");
    public static final Key<List<Submission>> PSI_KEY_SUBMISSION_LIST = Key.create("CurrentSubmission");

}
