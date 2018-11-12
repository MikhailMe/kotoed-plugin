package gui.Items;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static gui.Utils.Strings.*;

public class SignUp extends JDialog {

    private JPanel contentPane;

    private JTextField usernameField;
    private JTextField emailField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton signUpButton;
    private JButton cancelButton;

    public SignUp() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(signUpButton);

        signUpButton.addActionListener(e -> onOK());
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

        PromptSupport.setPrompt(CONFIRM_PASSWORD_TEXT, confirmPasswordField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, confirmPasswordField);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
