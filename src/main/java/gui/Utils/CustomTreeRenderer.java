package gui.Utils;

import gui.Stabs.CourseNode;
import gui.Stabs.ProjectNode;
import gui.Stabs.SubmissionNode;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import static gui.Utils.Strings.OPEN_ICON;
import static gui.Utils.Strings.CLOSED_ICON;

public class CustomTreeRenderer extends DefaultTreeCellRenderer {

    private ImageIcon icon;

    private static final String SPAN_FORMAT = "<span style='color:%s;'>%s</span>";

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();
        if (userObject instanceof CourseNode) {
            CourseNode course = (CourseNode) userObject;
            String text = String.format(SPAN_FORMAT, "blue", course.name);
            text += " [" + String.format(SPAN_FORMAT, "orange", course.status + "]");
            this.setText("<html>" + text + "</html>");
            //this.setIcon(employeeIcon);
        } else if (userObject instanceof ProjectNode) {
            ProjectNode project = (ProjectNode) userObject;
            String text = String.format(SPAN_FORMAT, "blue", project.name);
            text += " [" + String.format(SPAN_FORMAT, "orange", project.source + "]");
            this.setText("<html>" + text + "</html>");
        } else if (userObject instanceof SubmissionNode) {
            SubmissionNode submission = (SubmissionNode) userObject;
            String text = String.format(SPAN_FORMAT, "blue", submission.text);
            text += " [#" + String.format(SPAN_FORMAT, "orange", submission.number + "]");
            this.setText("<html>" + text + "</html>");
            if(submission.status)
                this.setIcon(new ImageIcon(getClass().getResource(OPEN_ICON)));
            else
                this.setIcon(new ImageIcon(getClass().getResource(CLOSED_ICON)));
        } else {
            String text = String.format(SPAN_FORMAT, "red", userObject);
            this.setText("<html>" + text + "</html>");
        }
        return this;
    }
}
