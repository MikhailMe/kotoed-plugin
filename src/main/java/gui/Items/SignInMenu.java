package gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utils.Strings.*;

public class SignInMenu extends VBox implements Initializable {

    //TODO: 24.10.2018 implement authorization
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    @FXML
    private Button backButton;

    public MainMenu mainMenu;

    public SignInMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEWS_DIR + SIGN_IN_MENU_VIEW + VIEWS_FORMAT));
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
        backButton.setOnMouseClicked((event -> mainMenu.ReloadMenu()));
        signInButton.setOnMouseClicked((event -> {
            if (usernameField.getText().equals(CREDENTIALS))
                if (passwordField.getText().equals(CREDENTIALS)) {
                    mainMenu.vbox.getChildren().clear();
                    //mainMenu.vbox.getChildren().add(new CoursesList(this));
                    return;
                }
        }));
    }

    public void Reload() {
        //TODO implement reload: push all child to array before clearing and then load on Reload
    }
}
