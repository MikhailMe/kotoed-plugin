package gui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.ui.JBColor;
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
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static gui.Utils.Strings.*;

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
        cheatButton = new JButton();
        cheatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DataContext dataContext = DataManager.getInstance().getDataContext();
                project = (Project) dataContext.getData(DataConstants.PROJECT);
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
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

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
            new Comments(new ArrayList<>(),(SubmissionNode)obj);
        } else {
        }
    }
    private void nodeClickedTwice(CourseNode course){
        displayInfo(course.toString());
    }
    private void nodeClickedTwice(ProjectNode project){
        displayInfo(project.toString());
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
