package plugin.gui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import plugin.gui.Items.Comments;
import plugin.gui.Items.SignInWindow;
import plugin.gui.Items.SignUpWindow;
import plugin.gui.Stabs.SubmissionNode;
import plugin.gui.Utils.SubmissionTreeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KotoedPlugin implements ToolWindowFactory {

    private JPanel panel;
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
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        signInButton.addActionListener(actionEvent -> onSignInButtonPressed());
        signUpButton.addActionListener(actionEvent -> onSignUpButtonPressed());
        ObtainProject();
    }

    @Override
    public void createToolWindowContent(@NotNull Project project,
                                        @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        commentsTab = new CommentsTab();
        buildTab = new BuildTab();

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content submission= contentFactory.createContent(panel, "Submissions", false);
        Content comment = contentFactory.createContent(commentsTab.getPanel(), "Comments", false);
        Content build = contentFactory.createContent(buildTab.getPanel(), "Build", false);
        toolWindow.getContentManager().addContent(submission);
        toolWindow.getContentManager().addContent(comment);
        toolWindow.getContentManager().addContent(build);

    }
    private void ObtainProject(){
        Thread thread = new Thread(){
            public void run(){
                DataContext dataContext = DataManager.getInstance().getDataContext();
                Project p =(Project) dataContext.getData(DataConstants.PROJECT);
                int count = 0;
                while(p == null)
                {
                    dataContext = DataManager.getInstance().getDataContext();
                    p = (Project) dataContext.getData(DataConstants.PROJECT);
                    System.out.println("Still null " + count++);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Project not null in " + count);
                KotoedPlugin.project = p;
            }
        };
        thread.start();
    }

    public void LoadSubmissions(@NotNull DefaultMutableTreeNode incomeTree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (int i = 0; i < 10; i++) {
            root.add(new DefaultMutableTreeNode(new SubmissionNode("Submission", i, (i % 2) == 1)));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel( root );
        tree.setModel(treeModel);
        tree.setRootVisible(false);

        if(!tree.isVisible())
            tree.setVisible(true);

        tree.setCellRenderer(new SubmissionTreeRenderer());
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

        commentsTab.updateComments();
    }

    private void parseObject(Object obj) {
        if (obj instanceof SubmissionNode) {
            //new Comments((SubmissionNode) obj);
        }
    }
    public void onSignInButtonPressed() {
        SignInWindow sign = new SignInWindow(this);
    }

    public void onSignUpButtonPressed() {
        SignUpWindow dialog = new SignUpWindow(this);
    }
}
