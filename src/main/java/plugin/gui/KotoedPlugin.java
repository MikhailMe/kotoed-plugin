package plugin.gui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import plugin.gui.Items.*;
import plugin.gui.Stabs.CourseNode;
import plugin.gui.Stabs.ProjectNode;
import plugin.gui.Stabs.SubmissionNode;
import plugin.gui.Utils.CustomTreeRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;

public class KotoedPlugin implements ToolWindowFactory {

    private JPanel panel;
    public static JButton cheatButton;
    private JButton signInButton;
    private JButton signUpButton;
    private JTree tree;
    private JPanel treePanel;
    private JScrollPane scrollPane;
    private ToolWindow myToolWindow;
    private CommentsTab commentsTab;
    private BuildTab buildTab;

    public static Project project;

    public KotoedPlugin() {
        cheatButton = new JButton();
        cheatButton.addActionListener(actionEvent -> {
            DataContext dataContext = DataManager.getInstance().getDataContext();
            project = (Project) dataContext.getData(DataConstants.PROJECT);
        });
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSignInButtonPressed();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSignUpButtonPressed();
            }
        });
    }

    @Override
    public void createToolWindowContent(@NotNull Project project,
                                        @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        commentsTab = new CommentsTab();
        buildTab = new BuildTab();

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content submission= contentFactory.createContent(panel, "Submissions", false);
        Content comment = contentFactory.createContent(commentsTab.panel, "Comments", false);
        Content build = contentFactory.createContent(buildTab.panel, "Build", false);
        toolWindow.getContentManager().addContent(submission);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(build);
    }

    public void LoadTree(@NotNull DefaultMutableTreeNode incomeTree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (int i = 0; i < 10; i++) {
            root.add(new DefaultMutableTreeNode(new SubmissionNode("SubmissionNode #" + i, i, true)));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel( root );
        tree.setModel(treeModel);
        tree.setRootVisible(false);

        if(!tree.isVisible())
            tree.setVisible(true);

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

        //treePanel.validate();
        //treePanel.repaint();
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
