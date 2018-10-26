package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @FXML
    private Label topLabel;
    @FXML
    private StackPane stackPane;
    @FXML
    public Label statusBar;
    @FXML
    public VBox vbox;

    private Button signInButton;
    private Button signUpButton;
    private List<Node> childs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
        //vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
        //vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
        //vbox.getChildren().add(new Course("/Icons/kotoed3.png","Kotlin"));
        ReloadMenu();
    }
    public void ReloadMenu()
    {
        vbox.getChildren().clear();
        signInButton = new Button("Sign In");
        signInButton.setOnMouseClicked((event -> {
            childs = new ArrayList<Node>();
            vbox.getChildren().clear();
            vbox.getChildren().add(new SignInMenu(this));
        }));
        signUpButton = new Button("Sign Up");
        signUpButton.setOnMouseClicked((event -> {
            vbox.getChildren().clear();
            vbox.getChildren().add(new SignUpMenu(this));
        }));

        signInButton.setMaxWidth(Double.MAX_VALUE);
        signUpButton.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().add(signInButton);
        vbox.getChildren().add(signUpButton);
    }
}
