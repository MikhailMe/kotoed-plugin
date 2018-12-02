package plugin.gui.Tabs;

import com.intellij.openapi.project.ProjectManager;
import lombok.Getter;
import plugin.core.eventbus.InformersImpl.GetInformer;
import plugin.core.sumbission.Submission;
import plugin.gui.Items.SignInWindow;
import plugin.gui.Items.SignUpWindow;
import plugin.gui.KotoedContext;
import plugin.gui.Stabs.SubmissionNode;
import plugin.gui.Utils.SubmissionTreeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import static plugin.gui.Utils.PsiKeys.PSI_KEY_HEADERS;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_SUBMISSION_LIST;
import static plugin.gui.Utils.Strings.*;
import static plugin.gui.Utils.Strings.ICON_SIZE;


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

        this.autoSubmitButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(UPLOAD_ICON)).getImage().getScaledInstance( ICON_SIZE, ICON_SIZE,  java.awt.Image.SCALE_SMOOTH )));
        this.refreshButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(REFRESH_ICON)).getImage().getScaledInstance( ICON_SIZE, ICON_SIZE,  java.awt.Image.SCALE_SMOOTH )));
    }
    public void loadSubmissions() {
        autoSubmitButton.setVisible(true);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");


        GetInformer informer = new GetInformer(
                CONFIGURATION,
                Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_HEADERS)));

        // 8 - courseId - то есть Functional Programming
        // 20 - сколько сабмишинов на одной странице
        // 0 - нулевая страница
        List<Submission> submissionList = informer.getSubmissions(8, 20, 0);

        for (Submission submission : submissionList) {
            // FIXME: здесь почему-то id проекта, а не сабмишина - нужно глянуть в котоеде
            String submissionName = submission.getName();
            long submissionId = submission.getId();
            // FIXME : здесь должен быть статус, которого нет :(
            boolean submissionStatus = true;
            SubmissionNode node = new SubmissionNode(submissionName, submissionId, submissionStatus);
            root.add(new DefaultMutableTreeNode(node));
        }

        KotoedContext.project.putUserData(PSI_KEY_SUBMISSION_LIST, submissionList);

        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        tree.setModel(treeModel);
        tree.setRootVisible(false);

        if (!tree.isVisible())
            tree.setVisible(true);

        tree.setCellRenderer(new SubmissionTreeRenderer());
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == DOUBLE_CLICK) {
                    // TODO: 11/29/2018 хотим чекаутиться на определенный сабмит
                }
            }
        });

        // TODO: 11/30/2018 когда поменяется этот метод - тогда расскоментить
        // TODO: пока что метод выдаёт эксепшн и всё ломается :(
        //commentsTab.loadComments();
    }
    private void onAutoSubmitPressed() {
        obtainProject();
    }

    private void obtainProject() {
        KotoedContext.project = ProjectManager.getInstance().getOpenProjects()[0];
    }
}
