package plugin.gui.Items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

import static plugin.gui.Utils.Strings.*;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ReloadMenu();
    }

    public void ReloadMenu() {
        vbox.getChildren().clear();
        LoadButton(SIGN_IN_BUTTON, new SignInMenu(this));
        LoadButton(SIGN_UP_BUTTON, new SignUpMenu(this));
    }

    public void LoadButton(@NotNull final String buttonName,
                           Node child) {
        Button button = new Button(buttonName);
        button.setOnMouseClicked((event -> {
            vbox.getChildren().clear();
            vbox.getChildren().add(child);
        }));
        button.setMaxWidth(Double.MAX_VALUE);
        vbox.getChildren().add(button);
    }
}
