package plugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import plugin.core.eventbus.InformersImpl.GetInformer;
import plugin.core.sumbission.Submission;
import plugin.gui.Items.SignInWindow;
import plugin.gui.Items.SignUpWindow;
import plugin.gui.Stabs.SubmissionNode;
import plugin.gui.Utils.SubmissionTreeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import static plugin.gui.Utils.PsiKeys.PSI_KEY_HEADERS;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_SUBMISSION_LIST;
import static plugin.gui.Utils.Strings.CONFIGURATION;
import static plugin.gui.Utils.Strings.DOUBLE_CLICK;


// TODO: 11/29/2018 rename me to KotoedContext
public class KotoedPlugin implements ToolWindowFactory {

    private JPanel panel;
    private JButton signInButton;
    private JButton signUpButton;
    private JButton autoSubmitButton;
    private JTree tree;
    private JPanel treePanel;
    private JScrollPane scrollPane;
    private ToolWindow myToolWindow;
    private CommentsTab commentsTab;
    private BuildTab buildTab;

    public static Project project;

    public KotoedPlugin() {

        autoSubmitButton.setVisible(false);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        signInButton.addActionListener(actionEvent -> onSignInButtonPressed());
        signUpButton.addActionListener(actionEvent -> onSignUpButtonPressed());
        autoSubmitButton.addActionListener(actionEvent -> onAutoSubmitPressed());
    }

    @Override
    public void createToolWindowContent(@NotNull Project project,
                                        @NotNull ToolWindow toolWindow) {
        buildTab = new BuildTab();
        myToolWindow = toolWindow;
        commentsTab = new CommentsTab();

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content submission = contentFactory.createContent(panel, "Submissions", false);
        Content comment = contentFactory.createContent(commentsTab.getPanel(), "Comments", false);
        Content build = contentFactory.createContent(buildTab.getPanel(), "Build", false);
        toolWindow.getContentManager().addContent(submission);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(build);
    }

    public void LoadSubmissions(@NotNull DefaultMutableTreeNode incomeTree) {
        signInButton.setVisible(false);
        signUpButton.setVisible(false);
        autoSubmitButton.setVisible(true);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");


        GetInformer informer = new GetInformer(
                CONFIGURATION,
                Objects.requireNonNull(project.getUserData(PSI_KEY_HEADERS)));

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

        project.putUserData(PSI_KEY_SUBMISSION_LIST, submissionList);

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

        //treePanel.validate();
        //treePanel.repaint();

        // TODO: 11/30/2018 когда поменяется этот метод - тогда расскоментить
        // TODO: пока что метод выдаёт эксепшн и всё ломается :(
        commentsTab.updateComments();
    }

    private void onSignInButtonPressed() {
        obtainProject();
        new SignInWindow(this);
    }

    private void onSignUpButtonPressed() {
        obtainProject();
        new SignUpWindow(this);
    }

    private void onAutoSubmitPressed() {
        obtainProject();
    }

    private void obtainProject() {
        project = ProjectManager.getInstance().getOpenProjects()[0];
    }
}
