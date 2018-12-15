package plugin.gui.Items;

import io.vertx.core.MultiMap;
import plugin.core.parser.GetParser;
import plugin.core.rest.Sender;
import plugin.gui.KotoedContext;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.event.*;

import static plugin.gui.Utils.PsiKeys.*;
import static plugin.gui.Utils.Strings.*;

public class SignInWindow extends JDialog {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton cancelButton;


    public SignInWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(signInButton);

        signInButton.addActionListener(e -> onSignIn());
        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e ->
                        onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setTitle(SIGN_IN);

        PromptSupport.setPrompt(USERNAME_TEXT, usernameField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, usernameField);

        PromptSupport.setPrompt(PASSWORD_TEXT, passwordField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, passwordField);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        System.out.println("here");
    }

    private void onSignIn() {
        String denizen = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        Sender sender = new Sender(CONFIGURATION);
        sender.signIn(denizen, password);
        String whoAmI = sender.getWhoAmI();
        long denizenId = GetParser.getDenizenId(whoAmI);
        if (!whoAmI.isEmpty()) {
            dispose();
            MultiMap headers = sender.getHeaders();
            KotoedContext.project.putUserData(PSI_KEY_DENIZEN, denizen);
            KotoedContext.project.putUserData(PSI_KEY_DENIZEN_ID, denizenId);
            KotoedContext.project.putUserData(PSI_KEY_HEADERS, headers);
            KotoedContext.project.putUserData(PSI_KEY_COOKIE, sender.getCookie());
            KotoedContext.checkCurrentProjectInKotoed();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    SIGN_IN_ERROR_MESSAGE,
                    SIGN_IN_ERROR,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
