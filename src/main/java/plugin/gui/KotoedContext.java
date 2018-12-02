package plugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import plugin.gui.Tabs.BuildTab;
import plugin.gui.Tabs.CommentsTab;
import plugin.gui.Tabs.SubmissionTab;

public class KotoedContext implements ToolWindowFactory {

    public static Project project;

    private static BuildTab buildTab;
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
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        Content build = contentFactory.createContent(buildTab.getPanel(), "Build", false);
        Content comment = contentFactory.createContent(commentsTab.getPanel(), "Comments", false);
        Content submission = contentFactory.createContent(submissionTab.getPanel(), "Submissions", false);

        toolWindow.getContentManager().addContent(build);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(submission);

        KotoedContext.project = ProjectManager.getInstance().getOpenProjects()[0];
    }

    public static void loadTabs() {

        commentsTab.loadComments();
        submissionTab.loadSubmissions();
    }
}
