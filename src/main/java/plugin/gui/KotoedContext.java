package plugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.core.eventbus.InformersImpl.GetInformer;
import plugin.core.sumbission.Submission;
import plugin.gui.Items.RegisterProjectWindow;
import plugin.gui.Items.SignInWindow;
import plugin.gui.Items.SignUpWindow;
import plugin.gui.Tabs.BuildTab;
import plugin.gui.Tabs.CommentsTab;
import plugin.gui.Tabs.SubmissionTab;

import javax.swing.*;

import java.util.List;
import java.util.Objects;

import static plugin.gui.Utils.PsiKeys.*;
import static plugin.gui.Utils.Strings.CONFIGURATION;
import static plugin.gui.Utils.Strings.SIGN_IN;
import static plugin.gui.Utils.Strings.SIGN_UP;

public class KotoedContext implements ToolWindowFactory {

    public static Project project;

    private static BuildTab buildTab;
    private static ToolWindow toolWindow;
    private static CommentsTab commentsTab;
    private static SubmissionTab submissionTab;

    static {
        buildTab = new BuildTab();
        commentsTab = new CommentsTab();
        submissionTab = new SubmissionTab();
    }

    @Override
    public void createToolWindowContent(@NotNull Project project,
                                        @NotNull ToolWindow toolWindow) {
        KotoedContext.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        JPanel panel = new JPanel();
        JButton signIn = new JButton(SIGN_IN);
        JButton signUp = new JButton(SIGN_UP);
        signIn.addActionListener(actionEvent -> onSignInButtonPressed());
        signUp.addActionListener(actionEvent -> onSignUpButtonPressed());
        panel.add(signIn);
        panel.add(signUp);

        Content init = contentFactory.createContent(panel, "Hello", false);
        toolWindow.getContentManager().addContent(init);

        KotoedContext.project = ProjectManager.getInstance().getOpenProjects()[0];
    }

    private void onSignInButtonPressed() {
        new SignInWindow();
    }

    private void onSignUpButtonPressed() {
        new SignUpWindow();
    }

    // TODO: 12/4/2018 только для показа на спринте ))))
    public static void checkCurrentProjectInKotoed() {
        // TODO: 02.12.2018 make some request to Kotoed and get project info
        // TODO: 02.12.2018 if project exists - load last commit, else - advice for register
        if (getProjectInfo()) {
            int temp = JOptionPane.showConfirmDialog(
                    null,
                    "Your project was found at the Kotoed. Do you want synchronized your project with found project?",
                    "Project found at the Kotoed",
                    JOptionPane.YES_NO_OPTION);
            if (temp != JOptionPane.NO_OPTION) {
                // TODO: 12/4/2018 написать метод синхронизации текущего проекта с котоедом
                loadTabs();
            }
        } else {
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Your project wasn't found at the Kotoed. Do you want to register your project at the Kotoed?",
                    "Project not found at the Kotoed",
                    JOptionPane.YES_NO_OPTION);
            if (n != JOptionPane.NO_OPTION) {
                new RegisterProjectWindow();
                loadTabs();
            }
        }
    }


    // TODO: 02.12.2018 future release - fix info getter from kotoed
    // TODO: 02.12.2018 if KotoedContext.project == getKotoedProjectFromKotoed()
    // TODO: then return true else call createproject window
    private static boolean getProjectInfo() {
        return true;
    }

    private static void loadTabs() {
        getFullProjectData();

        toolWindow.getContentManager().removeAllContents(true);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        Content build = contentFactory.createContent(buildTab.getPanel(), "Build", false);
        Content comment = contentFactory.createContent(commentsTab.getPanel(), "Comments", false);
        Content submission = contentFactory.createContent(submissionTab.getPanel(), "Submissions", false);

        toolWindow.getContentManager().addContent(build);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(submission);

        commentsTab.loadComments();
        submissionTab.loadSubmissions();
    }

    private static void getFullProjectData() {


        // TODO: 12/3/2018 get with data from work with projects
        int courseId = 8;
        int pageSize = 20;
        int currentPage = 0;


        GetInformer informer = new GetInformer(
                CONFIGURATION,
                Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_HEADERS)));

        // 8 - courseId - то есть Functional Programming
        // 20 - сколько сабмишинов на одной странице
        // 0 - нулевая страница
        List<Submission> submissionList = informer.getSubmissions(courseId, pageSize, currentPage);


        // TODO: 12/3/2018 get from @param submissionList
        int submissionId = 9255;

        List<Comment> commentList = informer.getComments(submissionId);

        KotoedContext.project.putUserData(PSI_KEY_REPO_URL, "repo url here");
        KotoedContext.project.putUserData(PSI_KEY_COMMENT_LIST, commentList);
        KotoedContext.project.putUserData(PSI_KEY_SUBMISSION_LIST, submissionList);
    }
}
