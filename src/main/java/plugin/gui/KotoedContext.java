package plugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import plugin.gui.Tabs.BuildTab;
import plugin.gui.Tabs.CommentsTab;
import plugin.gui.Tabs.SubmissionTab;

public class KotoedContext implements ToolWindowFactory {
    private static SubmissionTab submissionTab;
    private static CommentsTab commentsTab;
    private static BuildTab buildTab;

    public static Project project;
    @Override
    public void createToolWindowContent(@NotNull Project project,
                                        @NotNull ToolWindow toolWindow) {
        submissionTab = new SubmissionTab();
        buildTab = new BuildTab();
        commentsTab = new CommentsTab();

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content submission = contentFactory.createContent(submissionTab.getPanel(), "Submissions", false);
        Content comment = contentFactory.createContent(commentsTab.getPanel(), "Comments", false);
        Content build = contentFactory.createContent(buildTab.getPanel(), "Build", false);
        toolWindow.getContentManager().addContent(submission);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(build);

    }
    public static void loadTabs(){
        submissionTab.LoadSubmissions();
        commentsTab.loadComments();
    }
}
