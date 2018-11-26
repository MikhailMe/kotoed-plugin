package plugin.gui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import plugin.gui.Items.Comments;
import plugin.gui.Stabs.Comment;
import plugin.gui.Stabs.SubmissionNode;
import plugin.gui.Utils.CommentTreeRenderer;
import plugin.gui.Utils.SubmissionTreeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static plugin.gui.Utils.Strings.COMMENT_ICON;

@Data
public class CommentsTab {
    private JPanel comentPreview;
    private JPanel comentView;
    public JPanel panel;
    private JTree fileComentTree;
    private JButton button1;
    private JButton button2;
    private Comments comments;

    public void updateComments(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (int i = 0; i < 10; i++) {
            root.add(new DefaultMutableTreeNode(new Comment("Boris","10-01-2018","hue",12,"Main.java")));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel( root );
        fileComentTree.setModel(treeModel);
        fileComentTree.setRootVisible(false);

        if(!fileComentTree.isVisible())
            fileComentTree.setVisible(true);

        fileComentTree.setCellRenderer(new CommentTreeRenderer());
        fileComentTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            fileComentTree.getLastSelectedPathComponent();
                    if (node == null) return;
                    Object nodeInfo = node.getUserObject();
                    System.out.println("Comment pressed");
                    /*Duno what to do here*/
                }
            }
        });
        /*Get comment list from back and put into Comments object*/
        comments = new Comments(new Comment("Boris","10-01-2018","hue",12,"Main.java"));
        this.comentView.setLayout(new BorderLayout());
        this.comentView.add(comments.getContentPane());

        SetGutterIcons();

    }
    private void SetGutterIcons(/*Also here we need comments list*/)
    {
        /*for every coment using filename and linenumber - set icon - for all elements of list*/
        /*also need buffer for detecting already set icons to get rit of double icons*/
        int lineNumber = 12;
        Editor editor = FileEditorManager.getInstance(KotoedPlugin.project).getSelectedTextEditor();

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
    }
}
