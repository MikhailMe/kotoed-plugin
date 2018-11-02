package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utils.Strings.*;

public class Project extends HBox implements Initializable {

    @FXML
    private Label projectName;
    @FXML
    private Label projectSource;

    private String name;
    private String source;

    public Project(@NotNull final String name,
                  @NotNull final String source) {
        this.name = name;
        this.source = source;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEWS_DIR + PROJECT_VIEW + VIEWS_FORMAT));
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
        projectName.setText(name);
        projectSource.setText(source);
    }
}
