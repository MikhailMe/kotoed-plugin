package core;

import io.vertx.core.MultiMap;
import core.rest.Sender;
import core.util.Address;
import core.course.Course;
import core.comment.Comment;
import core.project.Project;
import core.eventbus.Informer;
import core.sumbission.Submission;

import java.util.List;
import java.util.ArrayList;

public class Main {

    private static final String CONFIGURATION = Address.GLOBAL;

    public static void main(String[] args) {

//        String denizen = "punsh";
//        String password = "punsh";
        String denizen = "vovasq";
        String password = "vovas123";


        Sender sender = new Sender(CONFIGURATION);
        String cookie = sender.signIn(denizen, password);

        MultiMap headers = sender.getHeaders(cookie);

        Informer informer = new Informer(CONFIGURATION, headers);

        System.out.println("******************Courses*****************************************************");


        List<Course> courses = new ArrayList<>();
        informer.getCourses(courses);
        courses.forEach(System.out::println);

// Projects mapping is not ready yet
//        System.out.println("******************Projects****************************************************");
//        List<Project> projects = new ArrayList<>();
//        informer.getProjects(projects);
//        projects.forEach(System.out::println);


        System.out.println("******************Submissions*************************************************");

        List<Submission> submissions = new ArrayList<>();
        informer.getSubmissions(8, 20, 0, submissions);
        submissions.forEach(System.out::println);

        System.out.println("******************Comments****************************************************");

        List<Comment> comments = new ArrayList<>();
        informer.getComments(9312, comments);
        comments.forEach(System.out::println);

    }

}
