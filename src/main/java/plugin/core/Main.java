package plugin.core;

import io.vertx.core.MultiMap;
import plugin.core.Util.Address;
import plugin.core.comment.Comment;
import plugin.core.course.Course;
import plugin.core.eventbus.Informer;
import plugin.core.project.Project;
import plugin.core.rest.Sender;
import plugin.core.sumbission.Submission;

import javax.xml.bind.annotation.XmlElementDecl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {

    private static final String CONFIGURATION = Address.GLOBAL;

    public static void main(String[] args) {

        String denizen = "vovasq";
        String password = "vovas123";

        Sender sender = new Sender(CONFIGURATION);
        String cookie = sender.signIn(denizen, password);

        MultiMap headers = sender.getHeaders(cookie);

        Informer informer = new Informer(CONFIGURATION, headers);
//
//        List<Course> courses = new ArrayList<>();
//        informer.getCourses(courses);
//        courses.forEach(System.out::println);

        /*List<Project> projects = new ArrayList<>();
        informer.getProjects(projects);
        projects.forEach(System.out::println);*/

        /*List<Submission> submissions = new ArrayList<>();
        informer.getSubmissions(8, 20, 0, submissions);
        submissions.forEach(System.out::println);*/

        List<Comment> comments = new ArrayList<>();
        informer.getComments(9312, comments);
        comments.forEach(System.out::println);

    }

}
