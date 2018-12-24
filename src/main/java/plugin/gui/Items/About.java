package plugin.gui.Items;

import javax.swing.*;
import java.awt.event.*;

public class About extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JEditorPane htmlPane;

    public About() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e ->
                onOK(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setResizable(false);

        htmlPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, false);

        pack();
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
