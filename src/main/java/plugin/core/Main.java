package plugin.core;

import io.vertx.core.MultiMap;
import plugin.core.course.Course;
import plugin.core.eventbus.InformersImpl.BaseInformer;
import plugin.core.eventbus.InformersImpl.CreateInfromer;
import plugin.core.rest.Sender;
import plugin.core.sumbission.Submission;
import plugin.core.util.Address;
import plugin.core.eventbus.InformersImpl.GetInformer;

import java.util.List;

public class Main {

    private static final String CONFIGURATION = Address.LOCAL;

    public static void main(String[] args) {

        String denizen = "punsh";
        String password = "punsh";

        Sender sender = new Sender(CONFIGURATION);
        String cookie = sender.signIn(denizen, password);

        MultiMap headers = sender.getHeaders(cookie);

        /*CreateInfromer createInformer = new CreateInfromer(CONFIGURATION, headers);
        createInformer.createProject();
        createInformer.createSubmission();
        createInformer.createComment();*/

        //GetInformer getInformer = new GetInformer(CONFIGURATION, headers);

        /*List<Course> courses = getInformer.getCourses();
        courses.forEach(System.out::println);*/

        /*List<Project> projects = getInformer.getProjects();
        projects.forEach(System.out::println);*/

        /*List<Submission> submissions = getInformer.getSubmissions(8, 20, 0);
        submissions.forEach(System.out::println);*/

        /*List<Comment> comments = getInformer.getComments(1111);
        comments.forEach(System.out::println);*/

    }

}
