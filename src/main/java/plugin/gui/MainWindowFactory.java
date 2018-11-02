package plugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import plugin.gui.Utils.Utils;
import javafx.embed.swing.JFXPanel;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static plugin.gui.Utils.Strings.*;

public class MainWindowFactory implements ToolWindowFactory {

    public static JFXPanel jFXPanel;

    @Override
    public void createToolWindowContent(@NotNull final Project project,
                                        @NotNull final ToolWindow toolWindow) {
        jFXPanel = new JFXPanel();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(jFXPanel, MAIN_SCENE_LABEL, false);
        toolWindow.getContentManager().addContent(content);
        try {
            jFXPanel.setScene(Utils.LoadScene(MAIN_SCENE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
