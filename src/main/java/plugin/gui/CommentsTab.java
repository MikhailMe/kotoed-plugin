package plugin.gui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import plugin.gui.Items.Comments;
import plugin.gui.Stabs.Comment;
import plugin.gui.Utils.CommentTreeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

import lombok.Data;

import static plugin.gui.Utils.Strings.COMMENT_ICON;

@Data
public class CommentsTab {

    private JPanel comentPreview;
    private JPanel comentView;
    private JPanel panel;
    private JTree fileComentTree;
    private JButton button1;
    private JButton button2;
    private Comments comments;

    public void update() {

    }

    public void updateComments() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (int i = 0; i < 10; i++) {
            root.add(new DefaultMutableTreeNode(new Comment("Boris", "10-01-2018", "hue", 12, "Main.java")));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        fileComentTree.setModel(treeModel);
        fileComentTree.setRootVisible(false);

        if (!fileComentTree.isVisible())
            fileComentTree.setVisible(true);

        fileComentTree.setCellRenderer(new CommentTreeRenderer());
        fileComentTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            fileComentTree.getLastSelectedPathComponent();
                    if (node == null) return;
                    Object nodeInfo = node.getUserObject();
                    System.out.println("Comment pressed");
                    /*Duno what to do here*/
                }
            }
        });
        // create random object
        Random ran = new Random();
        ArrayList<Comment> c = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            c.add(createStabComment(RandomStringUtils.randomAlphanumeric(9),
                    ran.nextInt(30) + "-" + ran.nextInt(12) + "-2018",
                    RandomStringUtils.randomAlphanumeric(128),
                    ran.nextInt(30) % 2 == 1 ? "Main.java" : "Test.java",
                    ran.nextInt(30)));
        /*Get comment list from back and put into Comments object*/
        comments = new Comments(c);
        this.comentView.setLayout(new BorderLayout());
        this.comentView.add(comments.getContentPane());

        SetGutterIcons(c);

    }

    private plugin.gui.Stabs.Comment createStabComment(@NotNull String userName,
                                                       @NotNull String date,
                                                       @NotNull String text,
                                                       @NotNull String fileName,
                                                       final int lineNumber) {
        return new plugin.gui.Stabs.Comment(userName, date, text, lineNumber, fileName);
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
    private void SetGutterIcons(List<Comment> c) {

        /*for every coment using filename and linenumber - set icon - for all elements of list*/
        /*also need buffer for detecting already set icons to get rit of double icons*/
        List<Integer> markedLines = new ArrayList<>();
        Map<String, ArrayList<Integer>> m = new HashMap<>();
        for (Comment com : c) {
            if (!m.containsKey(com.getFileName()))
                m.put(com.getFileName(), new ArrayList<>());
            if (m.get(com.getFileName()).contains(com.getLineNumber()))
                return;
            /* Вот тут реальный костыль: крч нам надо отрисовать иконки гаттера по всем файлам
             * для этого я беру название файла из комента, открываю его в едиторе, рисую иконку,и так по всем коментам,
             * но кекус в том что если закрыть файл и открыть - иконки надо рисовать заного,
             * тут надо углубиться в идею гаттера но мне впадло и нет времени,
             * скоро зачетная неделя и нам надо "херак херак и в продакшн" - потом пофиксим*/
            File file = new File(KotoedPlugin.project.getBasePath() + "/src/" + com.getFileName());

            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            FileEditorManager.getInstance(KotoedPlugin.project).openFile(Objects.requireNonNull(virtualFile), true);

            Editor editor = FileEditorManager.getInstance(KotoedPlugin.project).getSelectedTextEditor();
            if (editor == null) return;
            int totalLineCount = editor.getDocument().getLineCount();
            if (com.getLineNumber() > totalLineCount) return;
            if (!markedLines.contains(com.getLineNumber())) {
                final RangeHighlighter rangeHighLighter = editor.getMarkupModel().addLineHighlighter(com.getLineNumber() - 1, 0, null);
                rangeHighLighter.setGutterIconRenderer(CreateGutterIconRenderer());
                //Сохраняем чтоб небыло повторений отрисовки иконок
                ArrayList<Integer> a = m.get(com.getFileName());
                a.add(com.getLineNumber());
                m.put(com.getFileName(), a);
            }
        }
    }

    private GutterIconRenderer CreateGutterIconRenderer() {
        GutterIconRenderer gutterIconRenderer = new GutterIconRenderer() {
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
        return gutterIconRenderer;
    }
}
