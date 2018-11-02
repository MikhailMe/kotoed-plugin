package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utils.Strings.*;
import static gui.Utils.Strings.VIEWS_FORMAT;

public class MainMenu implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    public VBox vbox;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;

    private String oldStyle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void ReloadMenu() {
        vbox.getChildren().clear();
    }
    @FXML
    private void onSignInButtonAction(){
        //vbox.getChildren().clear();
        //vbox.getChildren().add(new SignInMenu(this));

        SignIn sign = new SignIn();

        if (sign.username.equals(CREDENTIALS))
            if (sign.password.equals(CREDENTIALS)) {
                vbox.getChildren().add(new CoursesList());
                return;
            }
    }
    @FXML
    private void onSignUpButtonAction(){
        //vbox.getChildren().clear();
        //vbox.getChildren().add(new SignUpMenu(this));
        SignUp dialog = new SignUp();
        dialog.pack();
        dialog.setVisible(true);
    }
}
