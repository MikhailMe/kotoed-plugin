package plugin.gui.Items;

import javafx.util.Pair;
import lombok.Getter;
import plugin.core.eventbus.InformersImpl.CreateInfromer;
import plugin.gui.KotoedContext;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import plugin.core.comment.Comment;
import plugin.gui.Utils.CommentTreeItem;

import static plugin.gui.Utils.PsiKeys.*;
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

    public Comments(@NotNull CommentTreeItem commentTree) {
        registerActions();
        addBorders();
        addComments(commentTree);
    }

    private void registerActions() {
        buttonOK.addActionListener(e -> onSend());
    }

    private void addBorders() {
        TitledBorder textAreaTitledBorder = BorderFactory.createTitledBorder(COMMENT_TEXT);
        textArea.setBorder(textAreaTitledBorder);

        // TODO add submission number
        TitledBorder commentPanelTitledBorder = BorderFactory.createTitledBorder(COMMENT_FOR/* add submision number*/);
        commentHolder.setBorder(commentPanelTitledBorder);
    }

    private void addComments(@NotNull CommentTreeItem commentTree) {
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));

        for (Comment comment : commentTree.getCommentList()) {
            commentPanel.add(new CommentItem(comment, KotoedContext.project));
        }

        scrollPane.getVerticalScrollBar().setUnitIncrement(35);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        prevMax = bar.getMaximum();
    }

    // FIXME: 12/4/2018 FIX PARAMETERS
    private void onSend() {
        //long denizenId = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_DENIZEN_ID));
        String denizen = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_DENIZEN));
        String currentSourceFile = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SOURCEFILE));
        long currentSourceLine = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SOURCELINE));
        long currentDate = System.currentTimeMillis();
        //long currentSubmissionId = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SUBMISSION_ID));

        Comment comment = new Comment();
        //comment.setAuthorId(denizenId);
        comment.setDenizenId(denizen);
        comment.setDatetime(currentDate);
        comment.setSourcefile(currentSourceFile);
        comment.setSourceline(currentSourceLine);
        //comment.setOriginalSubmissionId(currentSubmissionId);

        // TODO: 12/4/2018 check it !!!
        /*CreateInfromer createInfromer = new CreateInfromer(
                CONFIGURATION,
                Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_HEADERS)));
        createInfromer.createComment(
            comment.getOriginalSubmissionId(),
            comment.getAuthorId(),
            comment.getSourceline(),
            comment.getSourcefile(),
            comment.getText()
        );*/

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
            JOptionPane.showMessageDialog(
                    null,
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
