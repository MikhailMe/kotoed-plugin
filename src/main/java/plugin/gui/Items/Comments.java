package plugin.gui.Items;

import gherkin.lexer.Pa;
import javafx.util.Pair;
import lombok.Getter;
import plugin.gui.KotoedContext;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import plugin.core.comment.Comment;

import static plugin.gui.Utils.Strings.*;

public class Comments {

    private int prevMax;

    private JButton buttonOK;
    private JTextArea textArea;

    @Getter
    private JPanel contentPane;

    private JPanel commentPanel;
    private JPanel commentHolder;
    private JScrollPane scrollPane;

    public Comments(@NotNull Map<Pair<String, Long>, List<Comment>> structuredComments) {

        // this information must be take from Denizen object

        // TODO: 12/1/2018 подумать как грамотно всё прокинуть

        String userName = "Username";
        String date = DateTimeFormatter.ofPattern(TIME_PATERN).format(LocalDateTime.now());
        String text = "";
        String fileName = "Main.java";
        int lineNumber = 12;

        registerActions(userName, date, text, fileName, lineNumber);
        addBorders();
        addComments(structuredComments);
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

        // TODO add submission number
        TitledBorder commentPanelTitledBorder = BorderFactory.createTitledBorder(COMMENT_FOR/* add submision number*/);
        commentHolder.setBorder(commentPanelTitledBorder);
    }

    private void addComments(@NotNull Map<Pair<String, Long>, List<Comment>> structuredComments) {
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));

        for (Map.Entry<Pair<String, Long>, List<Comment>> entry : structuredComments.entrySet()) {
            // TODO написать добавление комментов
        }

        // TODO: remove me after write cycle
        /*for (Comment comment : comments) {
            commentPanel.add(new CommentItem(comment, KotoedContext.project));
        }*/

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

        // TODO: 12/1/2018 внести информацию в коммент
        Comment comment = new Comment();
        //comment.setDatetime();


        if (!textArea.getText().isEmpty()) {
            comment.setText(textArea.getText());
            commentPanel.add(new CommentItem(comment, KotoedContext.project));
            commentPanel.revalidate();
            scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
                if (e.getAdjustable().getMaximum() != prevMax) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevMax = e.getAdjustable().getMaximum();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null,
                    EMPTY_COMMENT_MESSAGE,
                    EMPTY_COMMENT,
                    JOptionPane.ERROR_MESSAGE);
        }
        textArea.setText("");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
