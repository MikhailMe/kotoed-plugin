package plugin.gui.Tabs;

import com.intellij.openapi.editor.markup.GutterIconRenderer;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.core.eventbus.InformersImpl.GetInformer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import lombok.Data;
import plugin.core.sumbission.Submission;
import plugin.gui.Items.Comments;
import plugin.gui.KotoedContext;
import plugin.gui.Utils.CommentTreeRenderer;

import static plugin.gui.Utils.PsiKeys.PSI_KEY_HEADERS;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_SUBMISSION_LIST;
import static plugin.gui.Utils.Strings.COMMENT_ICON;
import static plugin.gui.Utils.Strings.CONFIGURATION;
import static plugin.gui.Utils.Strings.DOUBLE_CLICK;

@Data
public class CommentsTab {

    private JPanel comentPreview;
    private JPanel comentView;
    private JPanel panel;
    private JTree fileComentTree;
    private JButton refreshButton;
    private Comments comments;

    public CommentsTab(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/refresh.png"));
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
        this.refreshButton.setIcon(new ImageIcon(newimg));
    }

    public void loadComments() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        fileComentTree.setModel(treeModel);
        fileComentTree.setRootVisible(false);

        if (!fileComentTree.isVisible()) fileComentTree.setVisible(true);

        fileComentTree.setCellRenderer(new CommentTreeRenderer());
        fileComentTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == DOUBLE_CLICK) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            fileComentTree.getLastSelectedPathComponent();
                }
            }
        });

        List<Submission> submissionList = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_SUBMISSION_LIST));


        final int submissionId = 9255;
        GetInformer informer = new GetInformer(
                CONFIGURATION,
                Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_HEADERS)));
        List<Comment> commentList = informer.getComments(submissionId);


        /*Get comment list from back and put into Comments object*/
        comments = new Comments(commentList);
        this.comentView.setLayout(new BorderLayout());
        this.comentView.add(comments.getContentPane());

        //SetGutterIcons(commentList);

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
    /*private void SetGutterIcons(List<Comment> c) {

        *//*for every coment using filename and linenumber - set icon - for all elements of list*//*
        *//*also need buffer for detecting already set icons to get rit of double icons*//*
        List<Integer> markedLines = new ArrayList<>();
        Map<String, List<Integer>> m = new HashMap<>();
        for (Comment com : c) {
            if (!m.containsKey(com.getFileName()))
                m.put(com.getFileName(), new ArrayList<>());
            if (m.get(com.getFileName()).contains(com.getLineNumber()))
                return;
            *//* Вот тут реальный костыль: крч нам надо отрисовать иконки гаттера по всем файлам
             * для этого я беру название файла из комента, открываю его в едиторе, рисую иконку,и так по всем коментам,
             * но кекус в том что если закрыть файл и открыть - иконки надо рисовать заного,
             * тут надо углубиться в идею гаттера но мне впадло и нет времени,
             * скоро зачетная неделя и нам надо "херак херак и в продакшн" - потом пофиксим*//*
            File file = new File(KotoedContext.project.getBasePath() + "/src/" + com.getFileName());

            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            FileEditorManager.getInstance(KotoedContext.project).openFile(Objects.requireNonNull(virtualFile), true);

            Editor editor = FileEditorManager.getInstance(KotoedContext.project).getSelectedTextEditor();
            if (editor == null) return;
            int totalLineCount = editor.getDocument().getLineCount();
            if (com.getLineNumber() > totalLineCount) return;
            if (!markedLines.contains(com.getLineNumber())) {
                final RangeHighlighter rangeHighLighter = editor.getMarkupModel().addLineHighlighter(com.getLineNumber() - 1, 0, null);
                rangeHighLighter.setGutterIconRenderer(CreateGutterIconRenderer());
                //Сохраняем чтоб небыло повторений отрисовки иконок
                List<Integer> a = m.get(com.getFileName());
                a.add(com.getLineNumber());
                m.put(com.getFileName(), a);
            }
        }
    }*/

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
