package plugin.gui.Tabs;

import com.intellij.openapi.util.Pair;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.gui.Items.Comments;
import plugin.gui.KotoedContext;
import plugin.gui.Utils.CommentTreeItem;
import plugin.gui.Utils.CommentTreeRenderer;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

import java.util.*;
import java.util.List;

import static plugin.gui.Utils.PsiKeys.*;
import static plugin.gui.Utils.Strings.DISPLAY;

@Data
public class CommentsTab {

    private JPanel comentPreview;
    private JPanel comentView;
    private JPanel panel;
    private JTree fileComentTree;
    private Comments comments;

    public CommentsTab() {
    }

    public void loadComments() {
        if (Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_SUBMISSION_LIST)).isEmpty()){
            return;
        }

        long submissionId = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SUBMISSION_ID));

        Map<Long, List<Comment>> map = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_COMMENT_LIST));

        List<Comment> commentList = map.get(submissionId);

        Map<Pair<String, Long>, List<Comment>> structuredComments = getStructuredComments(commentList);
        List<CommentTreeItem> commentItemsList = new ArrayList<>();

        for (Map.Entry<Pair<String, Long>, List<Comment>> i:structuredComments.entrySet()) {
            commentItemsList.add(new CommentTreeItem(i.getKey().getSecond(), i.getKey().getFirst(),i.getValue()));
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (CommentTreeItem commentTreeItem : commentItemsList) {
            root.add(new DefaultMutableTreeNode(commentTreeItem));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        fileComentTree.setModel(treeModel);
        fileComentTree.setRootVisible(false);

        if (!fileComentTree.isVisible()) fileComentTree.setVisible(true);

        fileComentTree.setCellRenderer(new CommentTreeRenderer());

        fileComentTree.addTreeSelectionListener(this::nodeSelected);
        this.comentView.setLayout(new BorderLayout());

        KotoedContext.project.putUserData(DISPLAY_GUTTER_ICONS, DISPLAY);
    }

    private void setCurrentFileAndLine(@NotNull String sourceFile,
                                       final long sourceLine) {
        KotoedContext.project.putUserData(PSI_KEY_CURRENT_SOURCEFILE, sourceFile);
        KotoedContext.project.putUserData(PSI_KEY_CURRENT_SOURCELINE, sourceLine);
    }

    private void nodeSelected(TreeSelectionEvent tse) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getNewLeadSelectionPath().getLastPathComponent();
        if (node == null) return;
        Object nodeInfo = node.getUserObject();
        CommentTreeItem treeItem = (CommentTreeItem) nodeInfo;
        UpdateCommentArea(new Comments(treeItem).getContentPane());
        setCurrentFileAndLine(treeItem.getSourcefile(), treeItem.getSourceline());
    }

    private void UpdateCommentArea(JPanel p) {
        this.comentView.removeAll();
        this.comentView.add(p);
        this.comentView.revalidate();
        this.comentView.repaint();
    }

    private Map<Pair<String, Long>, List<Comment>> getStructuredComments(List<Comment> commentList) {
        Map<Pair<String, Long>, List<Comment>> structuredComments = new HashMap<>();
        commentList.forEach(comment -> {
            String sourceFile = comment.getSourcefile();
            long sourceLine = comment.getSourceline();
            Pair<String, Long> pair = new Pair<>(sourceFile, sourceLine);

            if (structuredComments.containsKey(pair)) {
                structuredComments.get(pair).add(comment);
            } else {
                List<Comment> comments = new ArrayList<>();
                comments.add(comment);
                structuredComments.put(pair, comments);
            }
        });
        return structuredComments;
    }
}
