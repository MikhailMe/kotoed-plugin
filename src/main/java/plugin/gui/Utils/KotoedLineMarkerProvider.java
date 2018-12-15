package plugin.gui.Utils;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.*;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.gui.KotoedContext;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static plugin.gui.Utils.PsiKeys.DISPLAY_GUTTER_ICONS;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_COMMENT_LIST;

public class KotoedLineMarkerProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            Collection<? super RelatedItemLineMarkerInfo> result) {
        if(KotoedContext.project.getUserData(DISPLAY_GUTTER_ICONS).equals("Display")){

            List<Comment> commentList = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_COMMENT_LIST));

            if(commentList != null){

                for (Comment c: commentList) {
                    File file = new File(KotoedContext.project.getBasePath() + "/src/" + c.getSourcefile());

                    VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);

                    if (element.getContainingFile().getName().equals(virtualFile.getName())) {
                        int lineNumber = getStringLine(element.getContainingFile().getText(),element.getText());
                        if(lineNumber != c.getSourceline())
                            return;
                        if(!getFullStringByLine(element.getContainingFile().getText(),lineNumber).equals(element.getText()))
                            return;

                        result.add(getLineMarkerInfoForElem(element));
                    }
                }
            }
        }
    }

    public RelatedItemLineMarkerInfo<PsiElement> getLineMarkerInfoForElem(@NotNull PsiElement element) {
        // TODO: 16.12.18 implement tooltip due to comment
        Function<PsiElement, String> tooltipProvider = element1 -> {
            final StringBuilder tooltip = new StringBuilder();
            tooltip.append("mytool");

            return tooltip.length() == 0 ? null : tooltip.toString();
        };
        final GutterIconNavigationHandler<PsiElement> navHandler = new GutterIconNavigationHandler<PsiElement>() {
            @Override
            public void navigate(MouseEvent e, PsiElement elt) {
                // TODO: 16.12.18 add action when haskell/kotlin support done
            }
        };
        return new RelatedItemLineMarkerInfo<PsiElement>(element,
                element.getTextRange(),
                AllIcons.Actions.Refresh,
                Pass.UPDATE_ALL,
                tooltipProvider,
                navHandler,
                GutterIconRenderer.Alignment.CENTER,
                NotNullLazyValue.createConstantValue(Collections.emptyList()));
    }
    private int getStringLine(String text, String key){
        String[] lines = text.split("\n");
        int lineNumber = 0;
        for (String line:lines) {
            if(line.contains(key))
                return lineNumber + 1;
            lineNumber++;
        }

        return -1;
    }
    private String getFullStringByLine(String text, int lineNumber){
        String[] lines = text.split("\n");
        return lines[lineNumber - 1].trim();
    }
}
