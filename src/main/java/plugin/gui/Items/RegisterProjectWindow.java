package plugin.gui.Items;

import lombok.Data;

import javax.swing.*;
import java.awt.event.*;

public class RegisterProjectWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField projectName;
    private JTextField repoUrl;
    private JRadioButton gitRadioButton;
    private JRadioButton mercurialRadioButton;
    private ButtonGroup group;

    public RegisterProjectWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
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

        this.gitRadioButton.setActionCommand("git");
        this.mercurialRadioButton.setActionCommand("mercurial");

        this.group = ((DefaultButtonModel)mercurialRadioButton.getModel()).getGroup();


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void onOK() {
        String repoType = this.group.getSelection().getActionCommand();
        String projectName = this.projectName.getText();
        String projectURL = this.repoUrl.getText();
        System.out.println(repoType + " " + projectName + " " + projectURL);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
