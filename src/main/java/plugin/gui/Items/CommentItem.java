package plugin.gui.Items;

import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import plugin.core.comment.Comment;
import plugin.gui.KotoedContext;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.util.Objects;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.border.TitledBorder;

import static plugin.gui.Utils.Strings.DOUBLE_CLICK;

public class CommentItem extends JPanel {

    private JPanel panel1;
    private JTextArea textArea;

    private final static Color color = JBColor.WHITE;

    public CommentItem(@NotNull Comment comment,
                       @NotNull Project project) {
        super();

        textArea.setText(comment.getText());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        TitledBorder title = BorderFactory.createTitledBorder(
                comment.getDenizenId() + " @ " + comment.getNormalDatetime() + " at line:" + comment.getSourceline());
        panel1.setBorder(title);

        this.add(panel1);
        this.setBackground(color);

        panel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == DOUBLE_CLICK) {
                    openFileInEditor(comment.getSourcefile(), (int) comment.getSourceline(), project);
                }
            }
        });
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == DOUBLE_CLICK) {
                    openFileInEditor(comment.getSourcefile(), (int) comment.getSourceline(), project);
                }
            }
        });
        this.setVisible(true);
    }

    private void openFileInEditor(@NotNull final String sourcefile,
                                  final int sourceline,
                                  @NotNull Project project) {
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

        if (editor == null) return;

        int totalLineCount = editor.getDocument().getLineCount();

        if (sourceline > totalLineCount) return;

        Document document = editor.getDocument();
        int startOffset = document.getLineStartOffset(sourceline - 1);
        int endOffset = document.getLineEndOffset(sourceline);

        SelectionModel selectionModel = editor.getSelectionModel();
        selectionModel.setSelection(startOffset, endOffset);

        File file = new File(project.getBasePath() + "/src/" + sourcefile);

        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        FileEditorManager.getInstance(project).openFile(Objects.requireNonNull(virtualFile), true);

    }
}
