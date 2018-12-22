package plugin.core.getInformer;

import io.vertx.core.MultiMap;
import org.junit.Test;
import plugin.core.comment.Comment;
import plugin.core.course.Course;
import plugin.core.eventbus.InformersImpl.GetInformer;
import plugin.core.parser.GetParser;
import plugin.core.project.Project;
import plugin.core.rest.Sender;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.RandomStringUtils;
import plugin.core.sumbission.Submission;
import plugin.gui.KotoedContext;

import java.util.List;
import java.util.Objects;

import static plugin.gui.Utils.PsiKeys.*;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_CURRENT_SOURCELINE;
import static plugin.gui.Utils.Strings.CONFIGURATION;

public class InformerTest {

    private Sender sender;

    private void initialize() {
        sender = new Sender("GLOBAL");
    }


    private GetInformer createInformer(){
        initialize();

        sender.signIn("mikhailme", "qwerty12345");
        String whoAmI = sender.getWhoAmI();
        long denizenId = GetParser.getDenizenId(whoAmI);
        if (!whoAmI.isEmpty()) {
            MultiMap headers = sender.getHeaders();

            GetInformer informer = new GetInformer(
                    CONFIGURATION,
                    Objects.requireNonNull(headers));

            return informer;
        }
        return null;
    }
    private List<Submission> getSubmissionsLocal(){
        int courseId = 8;
        int pageSize = 20;
        int currentPage = 0;

        GetInformer informer = createInformer();
        List<Submission> submissionList = informer.getSubmissions(courseId, pageSize, currentPage);

        return  submissionList;
    }

    @Test
    public void getSubmissionsCountTest() {
        List<Submission> subs = getSubmissionsLocal();

        assert subs.size() == 1;
    }
    @Test
    public void getSubmissionsTest() {
        List<Submission> subs = getSubmissionsLocal();

        assert !subs.isEmpty();
    }

    // TODO: 22/12/18 Fix, wait back
    public void getProjectTest() {
        List<Project> projects = createInformer().getProjects();

        assert !projects.isEmpty();
    }

    @Test
    public void getCourseTest() {

        List<Course> courses = createInformer().getCourses();

        assert !courses.isEmpty();
    }


    @Test
    public void getCommentTest() {
        GetInformer informer = createInformer();


        // TODO: 12/3/2018 get from @param submissionList
        int submissionId = 9255;

        List<Comment> commentList = informer.getComments(submissionId);

        assert !commentList.isEmpty();
    }

    @Test
    public void getCommentCountTest() {
        GetInformer informer = createInformer();


        // TODO: 12/3/2018 get from @param submissionList
        int submissionId = 9255;

        List<Comment> commentList = informer.getComments(submissionId);
        assert commentList.size() == 17;
    }

    public void createCommentTest ()  {
        //long denizenId = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_DENIZEN_ID));
        String denizen = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_DENIZEN));
        String currentSourceFile = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SOURCEFILE));
        long currentSourceLine = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SOURCELINE));
        long currentDate = System.currentTimeMillis();
        //long currentSubmissionId = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SUBMISSION_ID));

        Comment comment = new Comment();
        //comment.setAuthorId(denizenId);
        comment.setDenizenId(denizen);
        comment.setDatetime(currentDate);
        comment.setSourcefile(currentSourceFile);
        comment.setSourceline(currentSourceLine);
        //comment.setOriginalSubmissionId(currentSubmissionId);

//        GetInformer informer = createInformer();
//        informer.createComment(
//            comment.getOriginalSubmissionId(),
//            comment.getAuthorId(),
//            comment.getSourceline(),
//            comment.getSourcefile(),
//            comment.getText()
//        );

        assert true;
    }

    public void createProjectTest ()  {
        assert true;
    }

    public void createSubmissionTest ()  {
        assert true;
    }


}
