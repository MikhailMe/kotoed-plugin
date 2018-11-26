package plugin.gui;

import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Data;
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
                    //parseObject(nodeInfo);
                }
            }
        });
        comments = new Comments(new Comment("Boris","10-01-2018","hue",12,"Main.java"));
        //GridBagLayout gbc = new GridBagLayout();
        //gbc.columnWeight = gbc.columnWeights = 1.0;
        this.comentView.setLayout(new BorderLayout());
        this.comentView.add(comments.getContentPane());

    }
}
