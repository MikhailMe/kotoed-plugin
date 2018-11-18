package plugin.gui.Items;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import plugin.gui.KotoedPlugin;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Comment extends JPanel{
    private JPanel panel1;
    private JTextArea textArea;
    private Color color = Color.WHITE;

    //private Project project;

    private Color oldColor;
    public Comment(plugin.gui.Stabs.Comment comment, Project project){
        super();
        //this.project = project;
        textArea.setText(comment.text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel1.setPreferredSize(new Dimension(470,100));
        TitledBorder title = BorderFactory.createTitledBorder(comment.userName + " @ " + comment.date + " at line:" + comment.lineNumber);
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
                if( e.getClickCount() == 2) {
                    openFileInEditor(comment.fileName,comment.lineNumber, project);
                }
            }
        });
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() == 2) {
                    openFileInEditor(comment.fileName,comment.lineNumber, project);
                }
            }
        });


        this.setVisible(true);

    }
    private void openFileInEditor(String fileName,int lineNumber,@NotNull Project project){

        if(project == null)
            System.out.println("project null error");

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

        if( editor == null )
            return ;
        int totalLineCount = editor.getDocument().getLineCount();

        if( lineNumber > totalLineCount )
            return ;

        Document document = editor.getDocument();
        int startOffset = document.getLineStartOffset(lineNumber - 1);
        int endOffset = document.getLineEndOffset(lineNumber);

        SelectionModel selectionModel = editor.getSelectionModel();
        selectionModel.setSelection(startOffset,endOffset);

        File file = new File(project.getBasePath()+ "/src/" + fileName);

        if (!file.exists()) {
            System.out.println("File [" + file + "] not found");
        } else {
            System.out.println("File found");
        }

        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        FileEditorManager.getInstance(project).openFile(virtualFile,true);
        return ;
    }
}
