package plugin.gui.Utils;

import io.vertx.core.MultiMap;
import plugin.core.comment.Comment;
import com.intellij.openapi.util.Key;
import plugin.core.course.Course;
import plugin.core.project.Project;
import plugin.core.sumbission.Submission;

import java.util.List;
import java.util.Map;

public class PsiKeys {

    public static final Key<String> PSI_KEY_REPO_URL = Key.create("Url");
    public static final Key<String> PSI_KEY_COOKIE = Key.create("Cookie");
    public static final Key<String> PSI_KEY_DENIZEN = Key.create("Denizen");
    public static final Key<MultiMap> PSI_KEY_HEADERS = Key.create("Headers");
    public static final Key<Long> PSI_KEY_DENIZEN_ID = Key.create("Denizen id");

    public static final Key<Long> PSI_KEY_PAGE_SIZE = Key.create("Page size");
    public static final Key<Long> PSI_KEY_CURRENT_PAGE = Key.create("Current page");
    public static final Key<Long> PSI_KEY_CURRENT_COURSE_ID = Key.create("Current course id");
    public static final Key<Long> PSI_KEY_CURRENT_PROJECT_ID = Key.create("Current project id");



    public static final Key<List<Course>> PSI_KEY_COURSES_LIST = Key.create("Courses");
    public static final Key<List<Project>> PSI_KEY_PROJECTS_LIST = Key.create("Projects");
    public static final Key<Map<Long, List<Comment>>> PSI_KEY_COMMENT_LIST = Key.create("Comments");
    public static final Key<List<Submission>> PSI_KEY_SUBMISSION_LIST = Key.create("Submissions");


    public static final Key<String> DISPLAY_GUTTER_ICONS = Key.create("DisplayGutterIcons");

    public static final Key<Long> PSI_KEY_CURRENT_SOURCELINE = Key.create("Current sourceline");
    public static final Key<String> PSI_KEY_CURRENT_SOURCEFILE = Key.create("Current sourcefile");
    public static final Key<Long> PSI_KEY_CURRENT_SUBMISSION_ID = Key.create("Current submission id");
}
