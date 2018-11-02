package gui.Items;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.Utils.Strings.*;

public class SignIn extends JDialog {
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton cancelButton;

    public String username;
    public String password;

    public SignIn() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(signInButton);

        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSignIn();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setTitle(SIGN_IN);

        PromptSupport.setPrompt(USERNAME_TEXT, usernameField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, usernameField);

        PromptSupport.setPrompt(PASSWORD_TEXT, passwordField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, passwordField);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void onSignIn() {
        // add your code here
        username = usernameField.getText();
        password = passwordField.getText();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
