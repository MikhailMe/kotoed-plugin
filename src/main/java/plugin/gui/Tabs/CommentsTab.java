package plugin.gui.Tabs;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

import lombok.Data;
import plugin.gui.Items.Comments;
import plugin.gui.KotoedContext;
import plugin.gui.Utils.CommentTreeItem;
import plugin.gui.Utils.CommentTreeRenderer;

import static plugin.gui.Utils.PsiKeys.PSI_KEY_COMMENT_LIST;
import static plugin.gui.Utils.Strings.*;

@Data
public class CommentsTab {

    private JPanel comentPreview;
    private JPanel comentView;
    private JPanel panel;
    private JTree fileComentTree;
    private JButton refreshButton;
    private Comments comments;

    public CommentsTab() {
        this.refreshButton.setIcon(new ImageIcon(new ImageIcon(getClass()
                .getResource(REFRESH_ICON))
                .getImage()
                .getScaledInstance(ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH)));
    }

    public void loadComments() {

        List<Comment> commentList = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_COMMENT_LIST));

        Map<Pair<String, Long>, List<Comment>> structuredComments = getStructuredComments(commentList);
        List<CommentTreeItem> commentItemsList = new ArrayList<>();

        for (Map.Entry<Pair<String, Long>, List<Comment>> i:structuredComments.entrySet()) {
            commentItemsList.add(new CommentTreeItem(i.getKey().getKey(),i.getKey().getValue(),i.getValue()));
        }
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (CommentTreeItem i:commentItemsList) {
            root.add(new DefaultMutableTreeNode(i));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        fileComentTree.setModel(treeModel);
        fileComentTree.setRootVisible(false);

        if (!fileComentTree.isVisible()) fileComentTree.setVisible(true);

        fileComentTree.setCellRenderer(new CommentTreeRenderer());

        fileComentTree.addTreeSelectionListener(evt -> nodeSelected(evt));
        this.comentView.setLayout(new BorderLayout());

        // TODO: 03.12.2018 DOWNLOAD SOME PROJECT FROM KOTOED AND TEST ON REAL PROJECT
        // TODO: 03.12.2018 also may be needs some fix in state machine to dont get errors
        //SetGutterIcons(commentItemsList);
    }
    private void nodeSelected(TreeSelectionEvent tse){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tse.getNewLeadSelectionPath().getLastPathComponent();
        if (node == null)
            return;
        Object nodeInfo = node.getUserObject();

        CommentTreeItem treeItem = (CommentTreeItem) nodeInfo;
        UpdateCommentArea(new Comments(treeItem).getContentPane());
    }
    private void UpdateCommentArea(JPanel p){
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

    // FIXME: 11/30/2018 на 131 строке - постоянный экспешн, у тебя там что-то генерится не очень, ты так говорил
    /*
     * 1) убрать параметер @param с
     * 2) вызывать этот метод при переходе на вкладку комментс
     * для отрисовки меджик холс
     * 3) почистить гатер либо подумать как его обновлять
     * 4) разобраться с PsiFile
     * 5) ПОВЕСИТЬ ЭКШОНЫ НА ЦЕШЕЧКИ !!!!!!!!!!!
     * */
    private void SetGutterIcons(List<CommentTreeItem> comments) {

     /*for every coment using filename and linenumber - set icon - for all elements of list
     also need buffer for detecting already set icons to get rit of double icons*/
        List<Integer> markedLines = new ArrayList<>();
        Map<String, List<Integer>> hashMap = new HashMap<>();
        for (CommentTreeItem comment : comments) {
            if (!hashMap.containsKey(comment.getSourcefile()))
                hashMap.put(comment.getSourcefile(), new ArrayList<>());
            if (hashMap.get(comment.getSourcefile()).contains((int) comment.getSourceline()))
                return;
            /*Вот тут реальный костыль: крч нам надо отрисовать иконки гаттера по всем файлам
             * для этого я беру название файла из комента, открываю его в едиторе, рисую иконку,и так по всем коментам,
             * но кекус в том что если закрыть файл и открыть - иконки надо рисовать заного,
             * тут надо углубиться в идею гаттера но мне впадло и нет времени,
             * скоро зачетная неделя и нам надо "херак херак и в продакшн" - потом пофиксим*/
            File file = new File(KotoedContext.project.getBasePath() + "/src/" + comment.getSourcefile());

            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            FileEditorManager.getInstance(KotoedContext.project).openFile(Objects.requireNonNull(virtualFile), true);

            Editor editor = FileEditorManager.getInstance(KotoedContext.project).getSelectedTextEditor();
            if (editor == null) return;
            int totalLineCount = editor.getDocument().getLineCount();
            if (comment.getSourceline() > totalLineCount) return;

            if (!markedLines.contains(comment.getSourceline())) {
                final RangeHighlighter rangeHighLighter = editor
                        .getMarkupModel()
                        .addLineHighlighter((int) comment.getSourceline() - 1, 0, null);
                rangeHighLighter.setGutterIconRenderer(CreateGutterIconRenderer());

                //Сохраняем чтоб небыло повторений отрисовки иконок
                List<Integer> savedList = hashMap.get(comment.getSourcefile());
                savedList.add((int) comment.getSourceline());
                hashMap.put(comment.getSourcefile(), savedList);
            }
        }
    }

    private GutterIconRenderer CreateGutterIconRenderer() {
        return new GutterIconRenderer() {
            @Override
            public boolean equals(Object obj) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @NotNull
            @Override
            public Icon getIcon() {
                return new ImageIcon(getClass().getResource(COMMENT_ICON));
            }
        };
    }
}
