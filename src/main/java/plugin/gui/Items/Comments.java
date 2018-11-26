package plugin.gui.Items;

import plugin.gui.KotoedPlugin;
import org.apache.commons.lang.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.time.LocalDateTime;
import javax.swing.border.TitledBorder;
import java.time.format.DateTimeFormatter;

import static plugin.gui.Utils.Strings.*;

public class Comments{

    private JButton buttonOK;
    private JTextArea textArea;
    private JPanel contentPane;
    private JPanel commentPanel;
    private JPanel commentHolder;
    private JScrollPane scrollPane;

    private int prevMax;
    private plugin.gui.Stabs.Comment comment;

    public Comments(@NotNull plugin.gui.Stabs.Comment comment) {
        KotoedPlugin.cheatButton.doClick();
        this.comment = comment;

        // this information must be take from Denizen object
        String userName = "Username";
        String date = DateTimeFormatter.ofPattern(TIME_PATERN).format(LocalDateTime.now());
        String text = "Some random message: " + RandomStringUtils.randomAlphanumeric(128);
        String fileName = "Main.java";
        int lineNumber = 12;

        registerActions(userName, date, text, fileName, lineNumber);
        addBorders();
        addComments(userName, date, text, fileName, lineNumber);
    }
    public JPanel getContentPane(){
        return contentPane;
    }
    private void registerActions(@NotNull String userName,
                                 @NotNull String date,
                                 @NotNull String text,
                                 @NotNull String fileName,
                                 final int lineNumber) {
        buttonOK.addActionListener(e -> onSend(userName, date, text, fileName, lineNumber));
    }

    private void addBorders() {
        TitledBorder textAreaTitledBorder = BorderFactory.createTitledBorder(COMMENT_TEXT);
        textArea.setBorder(textAreaTitledBorder);

        TitledBorder commentPanelTitledBorder = BorderFactory.createTitledBorder(COMMENT_FOR/* add submision number*/);
        commentHolder.setBorder(commentPanelTitledBorder);
    }

    private void addComments(@NotNull String userName,
                             @NotNull String date,
                             @NotNull String text,
                             @NotNull String fileName,
                             final int lineNumber) {
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));


        // create first comment
        plugin.gui.Stabs.Comment stabComment = createStabComment(userName, date, text, fileName, lineNumber);
        commentPanel.add(new plugin.gui.Items.Comment(stabComment, KotoedPlugin.project));

        // create second comment
        stabComment.setText("Some random message: " + RandomStringUtils.randomAlphanumeric(128));
        stabComment.setFileName("Test.java");
        commentPanel.add(new plugin.gui.Items.Comment(stabComment, KotoedPlugin.project));

        scrollPane.getVerticalScrollBar().setUnitIncrement(35);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        prevMax = bar.getMaximum();
    }

    private void onSend(@NotNull String userName,
                        @NotNull String date,
                        @NotNull String text,
                        @NotNull String fileName,
                        final int lineNumber) {
        plugin.gui.Stabs.Comment stabComment = createStabComment(userName, date, text, fileName, lineNumber);
        stabComment.setText(textArea.getText());
        commentPanel.add(new plugin.gui.Items.Comment(stabComment, KotoedPlugin.project));
        commentPanel.revalidate();
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (e.getAdjustable().getMaximum() != prevMax) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                prevMax = e.getAdjustable().getMaximum();
            }
        });
        textArea.setText("");
    }

    private plugin.gui.Stabs.Comment createStabComment(@NotNull String userName,
                                                       @NotNull String date,
                                                       @NotNull String text,
                                                       @NotNull String fileName,
                                                       final int lineNumber) {
        return new plugin.gui.Stabs.Comment(userName, date, text, lineNumber, fileName);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
