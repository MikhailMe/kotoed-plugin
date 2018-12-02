package plugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import plugin.gui.Items.RegisterProjectWindow;
import plugin.gui.Items.SignInWindow;
import plugin.gui.Items.SignUpWindow;
import plugin.gui.Tabs.BuildTab;
import plugin.gui.Tabs.CommentsTab;
import plugin.gui.Tabs.SubmissionTab;

import javax.swing.*;

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
        JButton signIn = new JButton("Sign In");
        JButton signUp = new JButton("Sign Up");
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

    public static void checkCurrentProjectInKotoed() {
        // TODO: 02.12.2018 make some request to Kotoed and get project info 
        // TODO: 02.12.2018 if project exists - load last commit,else - advice for register
        if (getProjectInfo()) {
            loadTabs();
        } else {
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to register your project?",
                    "Project not found at Kotoed",
                    JOptionPane.YES_NO_OPTION);
            if (n != 1) {
                new RegisterProjectWindow();
                loadTabs();
            }
        }

    }

    // TODO: 02.12.2018 future release - fix info getter from kotoed
    // TODO: 02.12.2018 if KotoedContext.project == getKotoedProjectFromKotoed()
    // TODO: then return true else call createproject window
    private static boolean getProjectInfo() {
        return false;
    }


    public static void loadTabs() {
        toolWindow.getContentManager().removeAllContents(true);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        Content submission = contentFactory.createContent(submissionTab.getPanel(), "Submissions", false);
        Content comment = contentFactory.createContent(commentsTab.getPanel(), "Comments", false);
        Content build = contentFactory.createContent(buildTab.getPanel(), "Build", false);

        toolWindow.getContentManager().addContent(submission);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(build);


        commentsTab.loadComments();
        submissionTab.loadSubmissions();
    }
}
