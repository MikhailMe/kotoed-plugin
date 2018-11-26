package plugin.gui.Items;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
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

import static plugin.gui.Utils.Strings.COMMENT_ICON;

public class Comment extends JPanel {

    private JPanel panel1;
    private JTextArea textArea;

    private final static int DOUBLE_CLICK = 2;
    private final static Color color = JBColor.WHITE;

    public Comment(@NotNull plugin.gui.Stabs.Comment comment,
                   @NotNull Project project) {
        super();
        textArea.setText(comment.getText());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
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

        int lineNumber = 12;
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

        if (editor == null) return;

        int totalLineCount = editor.getDocument().getLineCount();

        if (lineNumber > totalLineCount) return;

        final RangeHighlighter rangeHighLighter = editor.getMarkupModel().addLineHighlighter(lineNumber - 1,0,null);
        GutterIconRenderer gutterIconRenderer = new GutterIconRenderer() {
            @Override
            public boolean equals(Object obj) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @NotNull
            @Override
            public Icon getIcon() {
                return new ImageIcon(getClass().getResource(COMMENT_ICON));
            }
        };
        rangeHighLighter.setGutterIconRenderer(gutterIconRenderer);
        for (RangeHighlighter item:editor.getMarkupModel().getAllHighlighters()) {
            System.out.println("item = [" + item + "]");
        }
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
