package plugin.gui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import plugin.gui.Items.Comments;
import plugin.gui.Items.SignInWindow;
import plugin.gui.Items.SignUpWindow;
import plugin.gui.Stabs.CourseNode;
import plugin.gui.Stabs.ProjectNode;
import plugin.gui.Stabs.SubmissionNode;
import plugin.gui.Utils.CustomTreeRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;

public class KotoedPlugin implements ToolWindowFactory {

    private JPanel panel;
    public static JButton cheatButton;
    private JButton signInButton;
    private JButton signUpButton;
    private JTree tree;
    private JPanel treePanel;
    private JPanel infoPanel;
    private JPanel toolbar;
    private ToolWindow myToolWindow;

    public static Project project;

    public KotoedPlugin() {
        signInButton.addActionListener(actionEvent -> onSignInButtonPressed());
        signUpButton.addActionListener(actionEvent -> onSignUpButtonPressed());
        cheatButton = new JButton();
        cheatButton.addActionListener(actionEvent -> {
            DataContext dataContext = DataManager.getInstance().getDataContext();
            project = (Project) dataContext.getData(DataConstants.PROJECT);
        });
    }

    @Override
    public void createToolWindowContent(@NotNull Project project,
                                        @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, "Kotoed Swing Test", false);
        toolWindow.getContentManager().addContent(content);
    }

    public void LoadTree(@NotNull DefaultMutableTreeNode incomeTree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        DefaultMutableTreeNode kotlinNode = new DefaultMutableTreeNode(new CourseNode("Kotlin", "icon", "Open"));
        for (int i = 0; i < 10; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ProjectNode("Kotlin project" + i, "git_url"));
            for (int j = 0; j < 10; j++)
                node.add(new DefaultMutableTreeNode(new SubmissionNode("SubmissionNode #" + j * i, j * i, true)));
            kotlinNode.add(node);
        }

        DefaultMutableTreeNode javaNode = new DefaultMutableTreeNode(new CourseNode("Java", "icon", "Closed"));
        for (int i = 0; i < 10; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ProjectNode("Java project " + i, "source_git"));
            for (int j = 0; j < 10; j++)
                node.add(new DefaultMutableTreeNode(new SubmissionNode("SubmissionNode #" + j * i, j * i, false)));
            javaNode.add(node);
        }

        root.add(kotlinNode);
        root.add(javaNode);

        tree = new JTree(root);
        tree.setCellRenderer(new CustomTreeRenderer());
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            tree.getLastSelectedPathComponent();
                    if (node == null) return;
                    Object nodeInfo = node.getUserObject();
                    parseObject(nodeInfo);
                }
            }
        });
        treePanel.add(new JScrollPane(tree));
        treePanel.validate();
        treePanel.repaint();
    }

    private void parseObject(Object obj) {
        if (obj instanceof CourseNode) {
            nodeClickedTwice((CourseNode) obj);
        } else if (obj instanceof ProjectNode) {
            nodeClickedTwice((ProjectNode) obj);
        } else if (obj instanceof SubmissionNode) {
            new Comments((SubmissionNode) obj);
        }
    }

    private void nodeClickedTwice(@NotNull CourseNode course) {
        displayInfo(course.toString());
    }

    private void nodeClickedTwice(@NotNull ProjectNode project) {
        displayInfo(project.toString());
    }

    private void displayInfo(String text) {
        JOptionPane.showMessageDialog(null,
                "You have pressed node:" + text,
                "Tree item pressed",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void onSignInButtonPressed() {
        SignInWindow sign = new SignInWindow(this);
    }

    public void onSignUpButtonPressed() {
        SignUpWindow dialog = new SignUpWindow(this);
    }
}
