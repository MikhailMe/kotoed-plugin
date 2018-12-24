package plugin.gui.Items;

import plugin.gui.KotoedContext;

import javax.swing.*;
import java.awt.event.*;

import static plugin.gui.Utils.PsiKeys.PSI_KEY_REPO_URL;

public class RegisterProjectWindow extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField projectName;
    private JTextField repoUrl;
    private JRadioButton gitRadioButton;
    private JRadioButton mercurialRadioButton;
    private ButtonGroup group;
    private boolean status = false;

    public RegisterProjectWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

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

        this.gitRadioButton.setActionCommand("git");
        this.mercurialRadioButton.setActionCommand("mercurial");

        this.group = ((DefaultButtonModel)mercurialRadioButton.getModel()).getGroup();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.repoUrl.setText(KotoedContext.project.getUserData(PSI_KEY_REPO_URL));
    }

    private void onOK() {
        String repoType = this.group.getSelection().getActionCommand();
        String projectName = this.projectName.getText();
        String projectURL = this.repoUrl.getText();
        System.out.println(repoType + " " + projectName + " " + projectURL);
        dispose();
        status = true;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        status = false;
    }

    public boolean getStatus(){
        return status;
    }
}
