package plugin.gui.Items;

import org.jdesktop.swingx.prompt.PromptSupport;
import plugin.core.rest.Sender;
import plugin.gui.SubmissionTab;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static plugin.gui.Utils.Strings.*;

public class SignUpWindow extends JDialog {

    private JPanel contentPane;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private JButton signInButton;
    private JButton cancelButton;

    // TODO: 11/30/2018 remove me!
    private SubmissionTab submissionTab;

    public SignUpWindow(SubmissionTab submissionTab) {
        this.submissionTab = submissionTab;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(signInButton);

        signInButton.addActionListener(e -> onOK());

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

        setTitle(SIGN_UP);

        PromptSupport.setPrompt(USERNAME_TEXT, usernameField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, usernameField);

        PromptSupport.setPrompt(EMAIL_TEXT, emailField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, emailField);

        PromptSupport.setPrompt(PASSWORD_TEXT, passwordField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, passwordField);

        PromptSupport.setPrompt(REPEAT_PASSWORD_TEXT, repeatPasswordField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, repeatPasswordField);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void onOK() {
        String denizen = usernameField.getText();
        String password = passwordField.getText();
        Sender sender = new Sender(CONFIGURATION);
        String singUpResponse = sender.signUp(denizen, password);
        if (sender.isSuccessSignUp(singUpResponse)) {
            dispose();
            new SignInWindow(submissionTab);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    SIGN_UP_ERROR_MESSAGE,
                    SIGN_UP_ERROR,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
}
