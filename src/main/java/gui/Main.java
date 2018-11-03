package gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
public class Main implements ToolWindowFactory {

    private JPanel panel;
    private JPanel toolbar;
    private JButton signInButton;
    private JButton signUpButton;
    private JButton button3;
    private JTree tree;
    private JPanel treePanel;

    private ToolWindow myToolWindow;

    private int buttonNumber = 0;

    public Main() {
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, "Kotoed Swing Test", false);
        toolWindow.getContentManager().addContent(content);
        //Current stab for tree
        //waiting for back to get data
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        //create the child nodes
        DefaultMutableTreeNode kotlinNode = new DefaultMutableTreeNode("Kotlin");
        for (int i = 0; i < 10; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode("Kotlin project " + i);
            for(int j = 0; j < 10; j++)
                node.add(new DefaultMutableTreeNode("Submission #" + j*i));
            kotlinNode.add(node);
        }

        DefaultMutableTreeNode javaNode = new DefaultMutableTreeNode("Java");
        for (int i = 0; i < 10; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode("Java project " + i);
            for(int j = 0; j < 10; j++)
                node.add(new DefaultMutableTreeNode("Submission #" + j*i));
            javaNode.add(node);
        }
        //add the child nodes to the root node
        root.add(kotlinNode);
        root.add(javaNode);

        tree = new JTree(root);
        treePanel.add(new JScrollPane(tree));
    }
}
