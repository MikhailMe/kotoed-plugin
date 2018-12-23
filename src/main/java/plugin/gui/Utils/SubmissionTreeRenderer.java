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
            String text = SUBMISSION + String.format(
                    SPAN_FORMAT,
                    ORANGE,
                    BRACKET_OPEN + "#" + submission.getNumber() + BRACKET_CLOSE);
            this.setText(HTML_OPEN + text + HTML_CLOSE);

            String statusIcon = submission.getStatus().equals(OPEN) ? OPEN_ICON : CLOSED_ICON;
            this.setIcon(new ImageIcon(getClass().getResource(statusIcon)));

        } else {
            String text = String.format(SPAN_FORMAT, RED, userObject);
            this.setText(HTML_OPEN + text + HTML_CLOSE);
        }
        return this;
    }
}
