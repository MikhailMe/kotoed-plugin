package plugin.gui.Items;

import org.jetbrains.annotations.NotNull;
import plugin.core.rest.Sender;
import plugin.gui.KotoedPlugin;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static plugin.gui.Utils.Strings.*;

public class SignIn extends JDialog {

    private JPanel contentPane;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton signInButton;
    private JButton cancelButton;

    private KotoedPlugin kotoedPlugin;

    public SignIn(KotoedPlugin kotoedPlugin) {
        this.kotoedPlugin = kotoedPlugin;
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
        contentPane.registerKeyboardAction(e -> onCancel(),
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

    private String signIn(@NotNull final String denizen,
                          @NotNull final String password) {
        System.out.println(1);
        Sender sender = new Sender(GLOBAL);
        System.out.println(2);
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println(3);
        CompletableFuture<String> cf = new CompletableFuture<>();
        System.out.println(4);
        cf.complete(sender.signIn(denizen, password));
        System.out.println(5);
        String cookie = cf.getNow(null);
        System.out.println(6);
        latch.countDown();
        System.out.println(7);
        try {
            latch.await();
            System.out.println(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(9);
        return cookie;
    }

    private void onSignIn() {
        final String denizen = usernameField.getText();
        final String password = passwordField.getText();
        System.out.println(0);
        String cookie = signIn(denizen, password);
        /*if (usernameField.getText().equals(CREDENTIALS))
            if (passwordField.getText().equals(CREDENTIALS)) {
                this.kotoedPlugin.LoadTree(new DefaultMutableTreeNode());
                dispose();
                return;
            }*/
        System.out.println(10);
        this.kotoedPlugin.LoadTree(new DefaultMutableTreeNode());
        dispose();
        /*       JOptionPane.showMessageDialog(null,
                AUTH_ERROR_MESSAGE,
                AUTH_ERROR,
                JOptionPane.ERROR_MESSAGE);*/
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
