package plugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.core.course.Course;
import plugin.core.eventbus.InformersImpl.GetInformer;
import plugin.core.sumbission.Submission;
import plugin.gui.Items.RegisterProjectWindow;
import plugin.gui.Tabs.BuildTab;
import plugin.gui.Tabs.CommentsTab;
import plugin.gui.Tabs.SubmissionTab;
import plugin.gui.ToolBar.ToolBar;

import javax.swing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static plugin.gui.Utils.PsiKeys.*;
import static plugin.gui.Utils.Strings.AUTH;
import static plugin.gui.Utils.Strings.CONFIGURATION;
import static plugin.gui.Utils.Strings.NOT_DISPLAY;

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
        //Clears toolWindow
        toolWindow.getContentManager().removeAllContents(true);

        //Getting factory for building ToolWindow
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        //Creating WindowPanel -> ActionToolBar -> Addint to ToolWindow for First Time Open
        SimpleToolWindowPanel noUserWindow = new SimpleToolWindowPanel(false, true);
        JPanel panel = new JPanel();
        noUserWindow.setContent(panel);
        noUserWindow.setToolbar(ToolBar.noUserToolBar(panel).getComponent());

        //Creating content for ToolWindow
        Content user = contentFactory.createContent(noUserWindow, AUTH, false);
        //Adding content to ToolWindow
        toolWindow.getContentManager().addContent(user);

        KotoedContext.project = ProjectManager.getInstance().getOpenProjects()[0];
        project.putUserData(DISPLAY_GUTTER_ICONS, NOT_DISPLAY);
    }

    public static void checkCurrentProjectInKotoed() {
        String wasFound = "Your project was found at the Kotoed." +
                " Do you want synchronized your project with found project?";
        String found = "Project found at the Kotoed";

        String wasNotFound = "Your project wasn't found at the Kotoed." +
                " Do you want to register your project at the Kotoed?";
        String notFound = "Project not found at the Kotoed";

        boolean result = getProjectInfo();

        int click = JOptionPane.showConfirmDialog(
                null,
                result ? wasFound : wasNotFound,
                result ? found : notFound,
                JOptionPane.YES_NO_OPTION);

        if (click != JOptionPane.NO_OPTION) {
            if (result)
                synchronizeWithKotoed();
            else {
                new RegisterProjectWindow();
                getProjectInfo();
            }
        }
        loadTabs();
    }

    private static boolean getProjectInfo() {
        GetInformer informer = new GetInformer(
                CONFIGURATION,
                Objects.requireNonNull(project.getUserData(PSI_KEY_HEADERS)));

        List<Course> courses = informer.getCourses();
        project.putUserData(PSI_KEY_COURSES_LIST, courses);

        project.putUserData(PSI_KEY_PAGE_SIZE, 20L);
        project.putUserData(PSI_KEY_CURRENT_PAGE, 0L);

        Map<Long, String> mapCoursesIdToName = new HashMap<>();
        courses.forEach(course -> mapCoursesIdToName.put(course.getId(), course.getName()));
        project.putUserData(PSI_KEY_MAP_COURSES_ID_TO_NAME, mapCoursesIdToName);

        for (Course course : courses) {
            List<plugin.core.project.Project> projects = informer.getProjects(
                    course.getId(),
                    Objects.requireNonNull(project.getUserData(PSI_KEY_PAGE_SIZE)),
                    Objects.requireNonNull(project.getUserData(PSI_KEY_CURRENT_PAGE))
            );
            for (plugin.core.project.Project proj : projects) {
                if (proj.getName().equals(project.getName())) {
                    project.putUserData(PSI_KEY_CURRENT_COURSE_ID, course.getId());
                    project.putUserData(PSI_KEY_CURRENT_PROJECT_ID, proj.getId());
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: 12/23/2018 Написать метод синхронизации с котоедом
    // TODO: 12/23/2018 То есть взять последнюю ревизию и откатиться на неё
    private static void synchronizeWithKotoed() {
        // TODO: 12/23/2018 work with GIT
    }

    private static void loadTabs() {
        getSubmissionsData();

        //Clears toolWindow
        toolWindow.getContentManager().removeAllContents(true);

        //Getting factory for building ToolWindow
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        //Creating WindowPanel -> ActionToolBar -> Addint to ToolWindow for CommentsTab
        SimpleToolWindowPanel commentPanel = new SimpleToolWindowPanel(false, true);
        commentPanel.setContent(commentsTab.getPanel());
        commentPanel.setToolbar(ToolBar.createCustomToolbar(
                commentsTab.getPanel(),
                toolWindow,
                "Comment").getComponent());

        //Creating WindowPanel -> ActionToolBar -> Addint to ToolWindow for BuildTab
        SimpleToolWindowPanel buildPanel = new SimpleToolWindowPanel(false, true);
        buildPanel.setContent(buildTab.getPanel());
        buildPanel.setToolbar(ToolBar.createCustomToolbar(
                buildTab.getPanel(),
                toolWindow,
                "Build").getComponent());

        //Creating WindowPanel -> ActionToolBar -> Addint to ToolWindow for SubmissionTab
        SimpleToolWindowPanel submissionPanel = new SimpleToolWindowPanel(false, true);
        submissionPanel.setContent(submissionTab.getPanel());
        submissionPanel.setToolbar(ToolBar.createCustomToolbar(
                submissionTab.getPanel(),
                toolWindow,
                "Submission").getComponent());

        //Creating content for ToolWindow
        Content build = contentFactory.createContent(buildPanel, "Build", false);
        Content comment = contentFactory.createContent(commentPanel, "Comments", false);
        Content submission = contentFactory.createContent(submissionPanel, "Submissions", false);

        //Adding content to ToolWindow
        toolWindow.getContentManager().addContent(build);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(submission);

        //Loading main data
        submissionTab.loadSubmissions();
        commentsTab.loadComments();
    }

    private static void getSubmissionsData() {
        GetInformer informer = new GetInformer(
                CONFIGURATION,
                Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_HEADERS)));

        // getting all submissions for current project
        List<Submission> submissionList = informer.getSubmissions(
                Objects.requireNonNull(project.getUserData(PSI_KEY_CURRENT_PROJECT_ID)),
                Objects.requireNonNull(project.getUserData(PSI_KEY_PAGE_SIZE)),
                Objects.requireNonNull(project.getUserData(PSI_KEY_CURRENT_PAGE))
        );

        KotoedContext.project.putUserData(PSI_KEY_SUBMISSION_LIST, submissionList);

        if (submissionList.isEmpty()) {
            return;
        }

        long currentSubmissionId = 0L;

        // getting all comments for any submission and put it to map
        Map<Long, List<Comment>> map = new HashMap<>();
        for (Submission submission : submissionList) {
            long submissionId = submission.getId();
            map.put(submissionId, informer.getComments(submissionId));
            if (submissionId > currentSubmissionId) currentSubmissionId = submissionId;
        }

        // TODO: 12/23/2018 set repo url
        KotoedContext.project.putUserData(PSI_KEY_REPO_URL, "monkey: repo url");
        KotoedContext.project.putUserData(PSI_KEY_COMMENT_LIST, map);
        KotoedContext.project.putUserData(PSI_KEY_CURRENT_SUBMISSION_ID, currentSubmissionId);
    }
}
