package plugin.core.eventbus;

import com.saffrontech.vertx.EventBusBridge;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.core.course.Course;
import plugin.core.parser.Parser;
import plugin.core.sumbission.Submission;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static plugin.core.Util.Address.*;

public class Informator {

    private static final String FIELD_ID = "id";
    private static final String FIELD_FIND = "find";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_PAGE_SIZE = "page_size";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_CURRENT_PAGE = "current_page";

    private MultiMap headers;
    private static List<Course> courses;
    private static List<Comment> commentsForSubmission;
    private static List<Submission> submissionsForCourse;

    public Informator(MultiMap headers) {
        this.headers = headers;
    }

    public List<Course> getCourses() {
        JsonObject message = new JsonObject().put(FIELD_TEXT, "");

        EventBusBridge.connect(URI.create(URL_GLOBAL_EVENTBUS), headers, eb -> {

            eb.send(URL_EVENTBUS_COURSES, message, reply -> {
                String jsonCourses = String.valueOf(reply.body());

                courses.addAll(Objects.requireNonNull(Parser.getCourses(jsonCourses)));

            /*for (Course course : Objects.requireNonNull(courses))
                System.out.println(course);*/

            });
        });
        return courses;
    }

    public void getComments(final int submissionNumber) {
        JsonObject message = new JsonObject().put(FIELD_ID, submissionNumber);
        EventBusBridge.connect(URI.create(URL_GLOBAL_EVENTBUS), headers, eb -> {
            eb.send(URL_EVENTBUS_COMMENTS, message, reply -> {
                String jsonComments = String.valueOf(reply.body());
                System.out.println(jsonComments);
                //commentsForSubmission = Parser.getComments(jsonComments);
            });
        });
    }

    public void getSubmissions(final int courseId) {
        int pageSize = 20;
        int currentPage = 0;
        JsonObject message = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject().put(FIELD_COURSE_ID, courseId));

        EventBusBridge.connect(URI.create(URL_GLOBAL_EVENTBUS), headers, eb -> {
            eb.send(URL_EVENTBUS_SUBMISSIONS_FOR_COURSE, message, reply -> {
                String jsonSubmissions = String.valueOf(reply.body());
                System.out.println(jsonSubmissions);
                //submissionsForCourse = Parser.getSubmissions(jsonSubmissions);
            });
        });
    }
}
