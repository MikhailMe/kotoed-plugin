package gui.Items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static gui.Utils.Strings.Stabs.COURSE_NAME;
import static gui.Utils.Strings.Stabs.ICON_URL;
import static gui.Utils.Strings.Views.COURSES_VIEW;
import static gui.Utils.Strings.Views.VIEWS_DIR;
import static gui.Utils.Strings.Views.VIEWS_FORMAT;

public class CoursesMenu extends VBox implements Initializable {
    @FXML
    private VBox vbox;

    private MainMenu mainMenu;

    public CoursesMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
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
        vbox.getChildren().add(new Course(ICON_URL,COURSE_NAME));
        vbox.getChildren().add(new Course(ICON_URL,COURSE_NAME));
        vbox.getChildren().add(new Course(ICON_URL,COURSE_NAME));
        vbox.getChildren().add(new Course(ICON_URL,COURSE_NAME));
    }
}