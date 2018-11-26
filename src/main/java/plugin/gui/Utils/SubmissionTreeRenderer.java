package plugin.gui.Utils;

import plugin.gui.Stabs.SubmissionNode;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import static plugin.gui.Utils.Strings.*;

public class SubmissionTreeRenderer extends DefaultTreeCellRenderer {

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

        if (userObject instanceof SubmissionNode) {
            SubmissionNode submission = (SubmissionNode) userObject;
            String text = submission.getText();
            text += BRACKET_OPEN + "#" + String.format(SPAN_FORMAT, ORANGE, submission.getNumber() + BRACKET_CLOSE);
            this.setText(HTML_OPEN + text + HTML_CLOSE);
            if (submission.getStatus()) this.setIcon(new ImageIcon(getClass().getResource(OPEN_ICON)));
            else this.setIcon(new ImageIcon(getClass().getResource(CLOSED_ICON)));
        } else {
            String text = String.format(SPAN_FORMAT, RED, userObject);
            this.setText(HTML_OPEN + text + HTML_CLOSE);
        }
        return this;
    }
}
