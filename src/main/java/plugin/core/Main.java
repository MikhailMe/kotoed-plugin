package plugin.core;

import io.vertx.core.MultiMap;
import plugin.core.parser.GetParser;
import plugin.core.project.Project;
import plugin.core.rest.Sender;
import plugin.core.sumbission.Submission;
import plugin.core.util.Address;
import plugin.core.eventbus.InformersImpl.GetInformer;

import java.util.List;

public class Main {

    private static final String CONFIGURATION = Address.GLOBAL;

    public static void main(String[] args) {

        String denizen = "alewa";
        String password = "alewa";

        Sender sender = new Sender(CONFIGURATION);
        String cookie = sender.signIn(denizen, password);

        System.out.println(cookie);

        MultiMap headers = sender.getHeaders();

        String whoAmI = sender.getWhoAmI();

        System.out.println(whoAmI);
        //System.out.println(whoAmI);

        System.out.println(GetParser.getDenizenId(whoAmI));


        /*CreateInformer createInformer = new CreateInformer(CONFIGURATION, headers);
        ProjectRecord pr = createInformer.createProject("punsh punsh", 446, 8, "git", "https://github.com/belyaev-mikhail/fp-practice-2018");
        System.out.println(pr.toString());
        SubmissionRecord sr = createInformer.createSubmission(531, "7da5fd6c0eeae9b2ba21a6808b0ed10de9a5de3d");
        System.out.println(sr.toString());
        createInformer.createComment(8615, 446, 2, "Task1_1.hs", "oops");*/

        GetInformer getInformer = new GetInformer(CONFIGURATION, headers);


        /*List<Course> courses = getInformer.getCourses();
        courses.forEach(System.out::println);*/

        /*List<Project> projects = getInformer.getProjects(8, 20, 0);
        projects.forEach(System.out::println);*/


        List<Submission> submissions = getInformer.getSubmissions(745, 20, 0);
        submissions.forEach(System.out::println);

        System.out.println(submissions.isEmpty());
        //List<Comment> comments = getInformer.getComments(9255);
        //comments.forEach(System.out::println);



    }

}
