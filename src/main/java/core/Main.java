package core;

import com.saffrontech.vertx.EventBusBridge;
import core.comment.Comment;
import core.course.Course;
import core.parser.Parser;
import core.rest.Sender;
import core.sumbission.Submission;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static core.Util.Address.*;

public class Main {

    private static final String FIELD_ID = "id";
    private static final String FIELD_FIND = "find";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_COOKIE = "Cookie";
    private static final String FIELD_PAGE_SIZE = "page_size";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_CURRENT_PAGE = "current_page";

    private static List<Course> courses;
    private static List<Comment> commentsForSubmission;
    private static List<Submission> submissionsForCourse;

    // TODO: 10/25/2018 Think about: "how use courses outside scope this method"
    private static void getCourses(@NotNull final EventBusBridge eb) {
        JsonObject message = new JsonObject().put(FIELD_TEXT, "");
        eb.send(URL_EVENTBUS_COURSES, message, reply -> {
            String jsonCourses = String.valueOf(reply.body());
            
            try {
                courses = Parser.getCourses(jsonCourses);

                for (Course course : courses)
                    System.out.println(course);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void getComments(@NotNull final EventBusBridge eb,
                                    final int submissionNumber) {
        JsonObject message = new JsonObject().put(FIELD_ID, submissionNumber);
        eb.send(URL_EVENTBUS_COMMENTS, message, reply -> {
            String jsonComments = String.valueOf(reply.body());
            System.out.println(jsonComments);
            //commentsForSubmission = Parser.getComments(jsonComments);
        });
    }

    private static void getSubmissions(@NotNull final EventBusBridge eb,
                                       final int courseId) {
        int pageSize = 20;
        int currentPage = 0;
        JsonObject message = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject().put(FIELD_COURSE_ID, courseId));
        eb.send(URL_EVENTBUS_SUBMISSIONS_FOR_COURSE, message, reply -> {
            String jsonSubmissions = String.valueOf(reply.body());
            System.out.println(jsonSubmissions);
            //submissionsForCourse = Parser.getSubmissions(jsonSubmissions);
        });
    }

    public static void main(String[] args) throws IOException {

        String configuration = "GLOBAL";
        String localDenizen = "punsh";
        String localPassword = "punsh";

        Sender sender = new Sender(configuration);
        String cookie = sender.signIn(localDenizen, localPassword);
        String whoAmI = sender.getWhoAmI();

        MultiMap headers = MultiMap.caseInsensitiveMultiMap().add(FIELD_COOKIE, cookie);

        EventBusBridge.connect(URI.create(URL_GLOBAL_EVENTBUS), headers, eb -> {

            // all courses in Kotoed
            //getCourses(eb);

            // all submissions of course with id == 8
            //getSubmissions(eb, 8);

            // all comments of submission with id == 8608
            //getComments(eb, 8608);

        });

    }

}
