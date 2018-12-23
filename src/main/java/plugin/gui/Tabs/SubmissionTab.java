package plugin.gui.Tabs;

import lombok.Getter;
//import plugin.core.sumbission.Submission;
import plugin.core.sumbission.Submission;
import plugin.gui.KotoedContext;
import plugin.gui.Stabs.SubmissionNode;
import plugin.gui.Utils.SubmissionTreeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import static plugin.gui.Utils.PsiKeys.*;
import static plugin.gui.Utils.Strings.*;

public class SubmissionTab {

    @Getter
    private JPanel panel;
    private JTree tree;
    private JPanel treePanel;
    private JScrollPane scrollPane;

    public SubmissionTab() {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    public void loadSubmissions() {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        List<Submission> submissionList =
                Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_SUBMISSION_LIST));

        for (Submission submission : submissionList) {
            long submissionId = submission.getId();
            String submissionStatus = submission.getState();
            SubmissionNode node = new SubmissionNode(submissionId, submissionStatus);
            root.add(new DefaultMutableTreeNode(node));
        }

        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        tree.setModel(treeModel);
        tree.setRootVisible(false);

        if (!tree.isVisible())
            tree.setVisible(true);

        tree.setCellRenderer(new SubmissionTreeRenderer());
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == SINGLE_CLICK) {
                    // TODO: 11/29/2018 хотим чекаутиться на определенный сабмит
                }
            }
        });
    }

}
