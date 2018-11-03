package gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import gui.Items.SignIn;
import gui.Items.SignUp;
import gui.Stabs.CourseNode;
import gui.Stabs.ProjectNode;
import gui.Stabs.SubmissionNode;
import gui.Utils.CustomTreeRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KotoedPlugin implements ToolWindowFactory {

    private JPanel panel;
    private JButton button3;
    private JButton signInButton;
    private JButton signUpButton;
    private JTree tree;
    private JPanel treePanel;
    private JPanel infoPanel;
    private JPanel toolbar;

    private ToolWindow myToolWindow;

    private int buttonNumber = 0;

    public KotoedPlugin() {
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
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, "Kotoed Swing Test", false);
        toolWindow.getContentManager().addContent(content);
    }

    public void LoadTree(DefaultMutableTreeNode incomeTree){
        //Current stab for tree
        //waiting for back to get data
        //create the root node

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        //create the child nodes
        DefaultMutableTreeNode kotlinNode = new DefaultMutableTreeNode(new CourseNode("Kotlin","icon", "Open"));
        for (int i = 0; i < 10; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ProjectNode("Kotlin project" + i, "git_url"));
            for(int j = 0; j < 10; j++)
                node.add(new DefaultMutableTreeNode( new SubmissionNode("SubmissionNode #" + j*i, j*i, true)));
            kotlinNode.add(node);
        }

        DefaultMutableTreeNode javaNode = new DefaultMutableTreeNode(new CourseNode("Java", "icon", "Closed"));
        for (int i = 0; i < 10; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ProjectNode("Java project " + i, "source_git"));
            for(int j = 0; j < 10; j++)
                node.add(new DefaultMutableTreeNode(new SubmissionNode("SubmissionNode #" + j*i, j*i, false)));
            javaNode.add(node);
        }
        //add the child nodes to the root node
        root.add(kotlinNode);
        root.add(javaNode);

        tree = new JTree(root);
        tree.setCellRenderer(new CustomTreeRenderer());
        treePanel.add(new JScrollPane(tree));
    }

    public void onSignInButtonPressed(){
        SignIn sign = new SignIn(this);
    }
    public void onSignUpButtonPressed(){
        SignUp dialog = new SignUp();
    }
}
