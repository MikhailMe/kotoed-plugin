package gui.Items;

import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.util.Objects;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.border.TitledBorder;

public class Comment extends JPanel {

    private JPanel panel1;
    private JTextArea textArea;

    private final static int WIDTH = 470;
    private final static int HEIGHT = 100;
    private final static int DOUBLE_CLICK = 2;
    private final static Color color = JBColor.WHITE;

    public Comment(@NotNull gui.Stabs.Comment comment,
                   @NotNull Project project) {
        super();
        textArea.setText(comment.getText());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel1.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        TitledBorder title = BorderFactory.createTitledBorder(comment.getUserName() + " @ " + comment.getDate() + " at line:" + comment.getLineNumber());
        panel1.setBorder(title);
        textArea.setEditable(false);
        this.add(panel1);
        this.setBackground(color);
        this.addMouseListener(new MouseAdapter() {
        });
        textArea.addMouseListener(new MouseAdapter() {
        });

        panel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == DOUBLE_CLICK) {
                    openFileInEditor(comment.getFileName(), comment.getLineNumber(), project);
                }
            }
        });
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == DOUBLE_CLICK) {
                    openFileInEditor(comment.getFileName(), comment.getLineNumber(), project);
                }
            }
        });
        this.setVisible(true);
    }

    private void openFileInEditor(@NotNull final String fileName,
                                  final int lineNumber,
                                  @NotNull Project project) {
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

        if (editor == null) return;

        int totalLineCount = editor.getDocument().getLineCount();

        if (lineNumber > totalLineCount) return;

        Document document = editor.getDocument();
        int startOffset = document.getLineStartOffset(lineNumber - 1);
        int endOffset = document.getLineEndOffset(lineNumber);

        SelectionModel selectionModel = editor.getSelectionModel();
        selectionModel.setSelection(startOffset, endOffset);

        File file = new File(project.getBasePath() + "/src/" + fileName);

        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        FileEditorManager.getInstance(project).openFile(Objects.requireNonNull(virtualFile), true);
    }
}
