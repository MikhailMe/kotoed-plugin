package plugin.core;

import io.vertx.core.MultiMap;
import plugin.core.project.Project;
import plugin.core.rest.Sender;
import plugin.core.util.Address;
import plugin.core.course.Course;
import plugin.core.comment.Comment;
import plugin.core.eventbus.Informer;
import plugin.core.sumbission.Submission;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    private static final String CONFIGURATION = Address.GLOBAL;

    public static void main(String[] args) {

        String denizen = "punsh";
        String password = "punsh";

        Sender sender = new Sender(CONFIGURATION);
        String cookie = sender.signIn(denizen, password);

        MultiMap headers = sender.getHeaders(cookie);

        Informer informer = new Informer(CONFIGURATION, headers);

        //informer.readProject(525);

        /*List<Course> courses = informer.getCourses();
        courses.forEach(System.out::println);*/

        /*List<Project> projects = informer.getProjects();
        projects.forEach(System.out::println);*/

        /*List<Submission> submissions = informer.getSubmissions(8, 20, 0);
        submissions.forEach(System.out::println);*/

        /*List<Comment> comments = informer.getComments(1111);
        comments.forEach(System.out::println);*/

    }

}
