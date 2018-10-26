package gui.Utils;

import com.sun.javafx.application.PlatformImpl;
import gui.MainWindowFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class Utils {
    public static Scene LoadScene(String viewName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setClassLoader(MainWindowFactory.class.getClassLoader());
        fxmlLoader.setLocation(MainWindowFactory.class.getResource("/Views/" + viewName + ".fxml"));
        return new Scene(fxmlLoader.load());
    }
}
