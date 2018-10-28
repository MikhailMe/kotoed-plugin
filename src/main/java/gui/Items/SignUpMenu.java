package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utils.Strings.Views.SIGN_UP_MENU_VIEW;
import static gui.Utils.Strings.Views.VIEWS_DIR;
import static gui.Utils.Strings.Views.VIEWS_FORMAT;

public class SignUpMenu extends VBox implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button backButton;

    private MainMenu mainMenu;


    public SignUpMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        FXMLLoader l = new FXMLLoader(getClass().getResource(VIEWS_DIR + SIGN_UP_MENU_VIEW + VIEWS_FORMAT));
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
        backButton.setOnMouseClicked((event -> mainMenu.ReloadMenu()));
    }
}
