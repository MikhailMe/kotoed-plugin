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

public class CoursesMenu extends VBox implements Initializable {
    @FXML
    private VBox vbox;
    private ArrayList<gui.Stabs.Course> stringSet;
    private MainMenu mainMenu;
    public CoursesMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        FXMLLoader l = new FXMLLoader(getClass().getResource("/Views/CoursesView.fxml"));
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
        vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
        vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
        vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
        vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
    }
}