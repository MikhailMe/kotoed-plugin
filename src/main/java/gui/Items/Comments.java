package gui.Items;

import com.intellij.ui.components.JBScrollPane;
import core.comment.Comment;
import core.sumbission.Submission;
import gui.Stabs.SubmissionNode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Comments extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JPanel commentPanel;
    private JTextArea textArea;

    private JPanel localPanel;
    private JBScrollPane pane;

    private int prevMax;

    private Color color = Color.WHITE;

    private SubmissionNode submission;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public Comments(ArrayList<Comment> messages, SubmissionNode submission) {
        this.submission = submission;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSend();
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

        TitledBorder textAreaTitledBorder = BorderFactory.createTitledBorder("Comment text:");
        textArea.setBorder(textAreaTitledBorder);

        TitledBorder commentPanelTitledBorder = BorderFactory.createTitledBorder("Comments for:" + submission.toString());
        commentPanel.setBorder(commentPanelTitledBorder);

        setTitle("Comments");

        addComments();

        this.setResizable(false);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void addComments(){
        localPanel = new JPanel();
        localPanel.setBackground(color);
        localPanel.setLayout(new BoxLayout(localPanel,BoxLayout.Y_AXIS));
        //Stab
        for (int i =0; i < 10;i++)
            localPanel.add(new gui.Items.Comment(
                    new gui.Stabs.Comment("Username" + i,dtf.format(now),"Some rangom message: " + genRandomString(128),12)
            ));
        pane = new JBScrollPane(localPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.getVerticalScrollBar().setUnitIncrement(35);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setBackground(color);
        JScrollBar bar = pane.getVerticalScrollBar();
        bar.setValue(bar.getMinimum());
        prevMax = bar.getMaximum();
        commentPanel.add(pane);
        commentPanel.setBackground(color);
    }
    private String genRandomString(int len) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
    private void onSend() {
        // add your code here
      localPanel.add(new gui.Items.Comment(
              new gui.Stabs.Comment("Username",dtf.format(now),textArea.getText(),12)
      ));
      commentPanel.revalidate();
        pane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(e.getAdjustable().getMaximum() != prevMax) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevMax = e.getAdjustable().getMaximum();
                }
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
