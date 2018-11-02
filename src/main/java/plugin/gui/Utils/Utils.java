package plugin.gui.Utils;

import plugin.gui.MainWindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public final class Utils {

    private static final String VIEWS = "/Views/";
    private static final String EXTENSION = ".fxml";

    public static Scene LoadScene(String viewName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setClassLoader(MainWindowFactory.class.getClassLoader());
        fxmlLoader.setLocation(MainWindowFactory.class.getResource(VIEWS + viewName + EXTENSION));
        return new Scene(fxmlLoader.load());
    }
}
