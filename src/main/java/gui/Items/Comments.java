package gui.Items;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import core.comment.Comment;
import core.sumbission.Submission;
import gui.KotoedPlugin;
import gui.Stabs.SubmissionNode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static gui.Utils.Strings.*;

public class Comments extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JPanel commentPanel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JPanel commentHolder;

    private int prevMax;
    private SubmissionNode submission;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_PATERN);
    LocalDateTime now = LocalDateTime.now();

    public Comments(ArrayList<Comment> messages, SubmissionNode submission) {
        KotoedPlugin.cheatButton.doClick();
        this.submission = submission;
        registerActions();
        addBorders();
        addComments();
        setParentParams();
    }
    private void registerActions(){
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void setParentParams(){
        setTitle(COMMENTS);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void addBorders(){
        TitledBorder textAreaTitledBorder = BorderFactory.createTitledBorder(COMMENT_TEXT);
        textArea.setBorder(textAreaTitledBorder);

        TitledBorder commentPanelTitledBorder = BorderFactory.createTitledBorder(COMMENT_FOR + submission.toString());
        commentHolder.setBorder(commentPanelTitledBorder);
    }
    private void addComments(){
        commentPanel.setLayout(new BoxLayout(commentPanel,BoxLayout.Y_AXIS));
        commentPanel.add(new gui.Items.Comment(
                        new gui.Stabs.Comment("Username1",dtf.format(now),"Some rangom message: " + genRandomString(128),12,"Main.java"), KotoedPlugin.project)
                );
        commentPanel.add(new gui.Items.Comment(
                new gui.Stabs.Comment("Username1",dtf.format(now),"Some rangom message: " + genRandomString(128),12,"Test.java"),KotoedPlugin.project)
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(35);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        prevMax = bar.getMaximum();
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
        commentPanel.add(new gui.Items.Comment(
              new gui.Stabs.Comment("Username",dtf.format(now),textArea.getText(),12,"Main.java"),KotoedPlugin.project
      ));
      commentPanel.revalidate();
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(e.getAdjustable().getMaximum() != prevMax) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevMax = e.getAdjustable().getMaximum();
                }
            }
        });
    }

    private void onCancel() {
        dispose();
    }
}
