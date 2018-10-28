package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utils.Strings.Stabs.COURSE_NAME;
import static gui.Utils.Strings.Stabs.ICON_URL;
import static gui.Utils.Strings.Views.COURSES_VIEW;
import static gui.Utils.Strings.Views.VIEWS_DIR;
import static gui.Utils.Strings.Views.VIEWS_FORMAT;

public class CoursesMenu extends VBox implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private Button backButton;

    private SignInMenu signInMenu;

    public CoursesMenu(SignInMenu signInMenu) {
        this.signInMenu = signInMenu;
        FXMLLoader l = new FXMLLoader(getClass().getResource(VIEWS_DIR + COURSES_VIEW + VIEWS_FORMAT));
        l.setController(this);
        l.setRoot(this);
        try {
            l.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 10; i++)
            vbox.getChildren().add(new Course(ICON_URL,COURSE_NAME));
        backButton.setOnMouseClicked((event -> {
            signInMenu.Reload();
        }));
    }
}