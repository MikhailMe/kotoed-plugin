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

import static gui.Utils.Strings.Messages.AUTH_ERROR;
import static gui.Utils.Strings.Messages.WELCOME;
import static gui.Utils.Strings.Stabs.CREDENTIALS;
import static gui.Utils.Strings.Views.SIGN_IN_MENU_VIEW;
import static gui.Utils.Strings.Views.VIEWS_DIR;
import static gui.Utils.Strings.Views.VIEWS_FORMAT;

public class SignInMenu extends VBox implements Initializable{
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
        FXMLLoader l = new FXMLLoader(getClass().getResource(VIEWS_DIR + SIGN_IN_MENU_VIEW + VIEWS_FORMAT));
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
        signInButton.setOnMouseClicked((event -> {
            if(usernameField.getText().equals(CREDENTIALS))
                if(passwordField.getText().equals(CREDENTIALS)) {
                    mainMenu.statusBar.setText(WELCOME);
                    mainMenu.vbox.getChildren().clear();
                    mainMenu.vbox.getChildren().add(new CoursesMenu(this));
                    return;
                }
            mainMenu.statusBar.setText(AUTH_ERROR);
        }));
    }

    public void Reload(){
        //TODO implement reload: push all child to array before clearing and then load on Reload
    }
}
