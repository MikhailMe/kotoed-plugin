package plugin.core.util;

public class Address {

    public static final String LOCAL = "LOCAL";
    public static final String GLOBAL = "GLOBAL";

    // Json fields
    public static final String NAME = "name";
    public static final String TEXT = "text";
    public static final String DENIZEN_ID = "denizen_id";
    public static final String REPO_TYPE = "repo_type";
    public static final String REPO_URL = "repo_url";
    public static final String PROJECT_ID = "project_id";
    public static final String COURSE_ID = "course_id";
    public static final String REVISION = "revision";
    public static final String PASSWORD = "password";
    public static final String AUTHOR_ID = "author_id";
    public static final String SOURCEFILE = "sourcefile";
    public static final String SOURCELINE = "sourceline";
    public static final String SUBMISSION_ID = "submission_id";

    public static final String URL_LOCAL_EVENTBUS = "http://localhost:9000/eventbus/";
    public static final String URL_LOCAL_WHO_AM_I = "http://localhost:9000/auth/whoAmI";
    public static final String URL_LOCAL_SIGN_IN = "http://localhost:9000/auth/login/doLogin";
    public static final String URL_LOCAL_SIGN_UP = "http://localhost:9000/auth/login/doSignUp";

    public static final String URL_GLOBAL_EVENTBUS = "http://kotoed.icc.spbstu.ru:9000/eventbus/";
    public static final String URL_GLOBAL_WHO_AM_I = "http://kotoed.icc.spbstu.ru:9000/auth/whoAmI";
    public static final String URL_GLOBAL_SIGN_IN = "http://kotoed.icc.spbstu.ru:9000/auth/login/doLogin";
    public static final String URL_GLOBAL_SIGN_UP = "http://kotoed.icc.spbstu.ru:9000/auth/login/doSignUp";

    public static final String URL_EVENTBUS_COURSES = "kotoed.api.course.search";
    public static final String URL_EVENTBUS_READ_PROJECT = "kotoed.api.project.read";
    public static final String URL_EVENTBUS_CREATE_PROJECT = "kotoed.api.project.create";
    public static final String URL_EVENTBUS_COMMENTS = "kotoed.api.submission.comments";
    public static final String URL_EVENTBUS_CREATE_SUBMISSION = "kotoed.api.submission.create";
    public static final String URL_EVENTBUS_CREATE_COMMENT = "kotoed.api.submission.comment.create";
    public static final String URL_EVENTBUS_COUNT_PROJECTS = "kotoed.api.project.searchForCourse.count";
    public static final String URL_EVENTBUS_SUBMISSIONS_FOR_COURSE = "kotoed.api.project.searchForCourse";


}
