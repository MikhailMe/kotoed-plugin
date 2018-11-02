package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utils.Strings.*;

public class ProjectList extends VBox implements Initializable {
    @FXML
    private VBox projectList;

    public ProjectList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEWS_DIR + PROJECT_LIST_VIEW + VIEWS_FORMAT));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 10; i++)
            projectList.getChildren().add(new Project("Kotlin","https://github.com/MikhailMe/kotoed-plugin.git"));
    }
}
