package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utils.Strings.*;

public class Course extends HBox implements Initializable {

    @FXML
    private Label courseName;

    @FXML
    private ImageView courseIcon;

    private String name;
    private String icon;
    private String oldStyle;

    public Course(@NotNull final String icon,
                  @NotNull final String name) {
        this.name = name;
        this.icon = icon;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEWS_DIR + COURSE_VIEW + VIEWS_FORMAT));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseName.setText(name);
        courseIcon.setImage(new Image(icon));
        this.setOnMouseEntered((event -> {
            oldStyle = this.getStyle();
            this.setStyle(STYLE);
        }));
        this.setOnMouseExited((event -> this.setStyle(oldStyle)));
    }
}
