package gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.sun.javafx.application.PlatformImpl;
import gui.Utils.Utils;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MainWindowFactory implements ToolWindowFactory {
    public static  JFXPanel jFXPanel;
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        jFXPanel = new JFXPanel();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(jFXPanel, "Navigation", false);
        toolWindow.getContentManager().addContent(content);
        try {
            jFXPanel.setScene(Utils.LoadScene("MainScene"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 10/23/2018 make gui great again

}
