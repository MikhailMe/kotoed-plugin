package plugin.gui.Utils;

import plugin.core.comment.Comment;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

import static plugin.gui.Utils.Strings.*;
import static plugin.gui.Utils.Strings.HTML_CLOSE;

public class CommentTreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();

        if (userObject instanceof CommentTreeItem) {
            CommentTreeItem comment = (CommentTreeItem) userObject;
            String text = comment.getSourcefile();
            text += BRACKET_OPEN + "#" + String.format(SPAN_FORMAT, ORANGE, comment.getSourceline() + BRACKET_CLOSE);
            this.setText(HTML_OPEN + text + HTML_CLOSE);
            this.setIcon(new ImageIcon(getClass().getResource(COMMENT_ICON)));
        } else {
            String text = String.format(SPAN_FORMAT, RED, userObject);
            this.setText(HTML_OPEN + text + HTML_CLOSE);
        }
        return this;
    }
}
