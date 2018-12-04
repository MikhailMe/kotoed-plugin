package plugin.gui.Utils;

import io.vertx.core.MultiMap;
import plugin.core.comment.Comment;
import com.intellij.openapi.util.Key;
import plugin.core.sumbission.Submission;

import java.util.List;

public class PsiKeys {

    public static final Key<String> PSI_KEY_REPO_URL = Key.create("Url");
    public static final Key<String> PSI_KEY_COOKIE = Key.create("Cookie");
    public static final Key<String> PSI_KEY_DENIZEN = Key.create("Denizen");
    public static final Key<MultiMap> PSI_KEY_HEADERS = Key.create("Headers");
    public static final Key<Long> PSI_KEY_DENIZEN_ID = Key.create("Denizen id");
    public static final Key<List<Comment>> PSI_KEY_COMMENT_LIST = Key.create("Comments");
    public static final Key<List<Submission>> PSI_KEY_SUBMISSION_LIST = Key.create("Submissions");

    public static final Key<Long> PSI_KEY_CURRENT_SOURCELINE = Key.create("Current sourceline");
    public static final Key<String> PSI_KEY_CURRENT_SOURCEFILE = Key.create("Current sourcefile");
    public static final Key<Integer> PSI_KEY_CURRENT_SUBMISSION_ID = Key.create("Current submission id");
}
