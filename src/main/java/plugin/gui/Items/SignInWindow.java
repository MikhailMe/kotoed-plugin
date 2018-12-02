package plugin.gui.Items;

import io.vertx.core.MultiMap;
import plugin.core.rest.Sender;
import plugin.gui.KotoedContext;
import plugin.gui.SubmissionTab;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;

import static plugin.gui.Utils.PsiKeys.PSI_KEY_COOKIE;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_HEADERS;
import static plugin.gui.Utils.Strings.*;

public class SignInWindow extends JDialog {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton cancelButton;

    // TODO: 11/30/2018 remove me !!!
    private SubmissionTab submissionTab;

    public SignInWindow(SubmissionTab submissionTab) {
        this.submissionTab = submissionTab;

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
    }

    private void onSignIn() {
        String denizen = usernameField.getText();
        String password = passwordField.getText();
        Sender sender = new Sender(CONFIGURATION);
        sender.signIn(denizen, password);
        if (!sender.getWhoAmI().isEmpty()) {
            MultiMap headers = sender.getHeaders();
            KotoedContext.project.putUserData(PSI_KEY_COOKIE, sender.getCookie());
            KotoedContext.project.putUserData(PSI_KEY_HEADERS, headers);
            KotoedContext.loadTabs();
            dispose();
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
