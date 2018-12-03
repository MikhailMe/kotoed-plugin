package plugin.gui.Tabs;

import lombok.Getter;
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
    private JButton autoSubmitButton;
    private JTree tree;
    private JPanel treePanel;
    private JScrollPane scrollPane;
    private JButton refreshButton;

    public SubmissionTab() {

        autoSubmitButton.setVisible(false);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        autoSubmitButton.addActionListener(actionEvent -> onAutoSubmitPressed());

        this.autoSubmitButton.setIcon(new ImageIcon(new ImageIcon(getClass()
                .getResource(UPLOAD_ICON))
                .getImage()
                .getScaledInstance(ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH)));

        this.refreshButton.setIcon(new ImageIcon(new ImageIcon(getClass()
                .getResource(REFRESH_ICON))
                .getImage()
                .getScaledInstance(ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH)));
    }

    public void loadSubmissions() {
        autoSubmitButton.setVisible(true);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        List<Submission> submissionList = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_SUBMISSION_LIST));

        for (Submission submission : submissionList) {
            // FIXME: здесь почему-то id проекта, а не сабмишина - нужно глянуть в котоеде
            String submissionName = submission.getName();
            long submissionId = submission.getId();
            // FIXME : здесь должен быть статус, которого нет :(
            boolean submissionStatus = true;
            SubmissionNode node = new SubmissionNode(submissionName, submissionId, submissionStatus);
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
                if (e.getClickCount() == CLICK_COUNT) {
                    // TODO: 11/29/2018 хотим чекаутиться на определенный сабмит
                }
            }
        });
    }

    private void onAutoSubmitPressed() {
        // TODO: 12/3/2018
        /*
        * вызываем окно идеи для коммита и коммитим
        * пушим измениния
        * посылаем сабмит-запрос в котоед
        * */


    }

}
