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

    private MainMenu mainMenu;

    public SignInMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        FXMLLoader l = new FXMLLoader(getClass().getResource("/Views/AuthView.fxml"));
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
            if(usernameField.getText().equals("admin"))
                if(passwordField.getText().equals("admin")) {
                    mainMenu.statusBar.setText("Hello admin");
                    mainMenu.vbox.getChildren().clear();
                    mainMenu.vbox.getChildren().add(new CoursesMenu(mainMenu));
                    return;
                }
            mainMenu.statusBar.setText("Wrong username or password!");
        }));
    }
}
