package plugin.core;

import io.vertx.core.MultiMap;
import plugin.core.course.Course;
import plugin.core.eventbus.InformersImpl.BaseInformer;
import plugin.core.eventbus.InformersImpl.CreateInfromer;
import plugin.core.rest.Sender;
import plugin.core.sumbission.Submission;
import plugin.core.util.Address;
import plugin.core.eventbus.InformersImpl.GetInformer;
import plugin.core.util.ProjectRecord;
import plugin.core.util.SubmissionRecord;

import java.util.List;

public class Main {

    private static final String CONFIGURATION = Address.LOCAL;

    public static void main(String[] args) {

        String denizen = "punsh";
        String password = "punsh";

        Sender sender = new Sender(CONFIGURATION);
        sender.signIn(denizen, password);

        MultiMap headers = sender.getHeaders();

        /*CreateInfromer createInformer = new CreateInfromer(CONFIGURATION, headers);

        ProjectRecord pr = createInformer.createProject("punsh punsh", 446, 8, "git", "https://github.com/belyaev-mikhail/fp-practice-2018");
        System.out.println(pr.toString());*/
        //SubmissionRecord sr = createInformer.createSubmission(531, "7da5fd6c0eeae9b2ba21a6808b0ed10de9a5de3d");
        //System.out.println(sr.toString());

        //createInformer.createComment(8615, 446, 2, "Task1_1.hs", "oops");




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
