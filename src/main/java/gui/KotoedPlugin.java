package gui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import gui.Items.Comments;
import gui.Items.SignIn;
import gui.Items.SignUp;
import gui.Stabs.CourseNode;
import gui.Stabs.ProjectNode;
import gui.Stabs.SubmissionNode;
import gui.Utils.CustomTreeRenderer;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static gui.Utils.Strings.*;

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
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() == 2) {
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
    private void parseObject (Object obj){
        if (obj instanceof CourseNode) {
            nodeClickedTwice((CourseNode)obj);
        } else if (obj instanceof ProjectNode) {
            nodeClickedTwice((ProjectNode)obj);
        } else if (obj instanceof SubmissionNode) {
            Comments comments = new Comments(new ArrayList<>(),(SubmissionNode)obj);
        } else {
        }
    }
    private void nodeClickedTwice(CourseNode course){
        displayInfo(course.toString());
    }
    private void nodeClickedTwice(ProjectNode project){
        displayInfo(project.toString());
    }
    private void nodeClickedTwice(SubmissionNode submission){
        displayInfo(submission.toString());
    }
    private void displayInfo(String text){
        JOptionPane.showMessageDialog(null,
                "You have pressed node:" + text,
                "Tree item pressed",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public void onSignInButtonPressed(){
        SignIn sign = new SignIn(this);
    }
    public void onSignUpButtonPressed(){
        SignUp dialog = new SignUp();
    }
}
