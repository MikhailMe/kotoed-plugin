package plugin.gui.Utils;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.*;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
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

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.*;

import static plugin.gui.Utils.PsiKeys.DISPLAY_GUTTER_ICONS;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_COMMENT_LIST;
import static plugin.gui.Utils.PsiKeys.PSI_KEY_CURRENT_SUBMISSION_ID;
import static plugin.gui.Utils.Strings.DISPLAY;

public class KotoedLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            Collection<? super RelatedItemLineMarkerInfo> result) {
        if (Objects.requireNonNull(KotoedContext.project.getUserData(DISPLAY_GUTTER_ICONS)).equals(DISPLAY)) {

            Map<Long, List<Comment>> map =
                    Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_COMMENT_LIST));

            List<Comment> commentList =
                    map.get(Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SUBMISSION_ID)));

            for (Comment comment : commentList) {

                File file = new File(KotoedContext.project.getBasePath() + "/src/" + comment.getSourcefile());
                VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);

                if (element.getContainingFile().getName().equals(Objects.requireNonNull(virtualFile).getName())) {

                    int lineNumber = getStringLine(element.getContainingFile().getText(), element.getText());

                    if (lineNumber != comment.getSourceline())
                        return;

                    if (!getFullStringByLine(element.getContainingFile().getText(), lineNumber).equals(element.getText()))
                        return;

                    NavigationGutterIconBuilder<PsiElement> builder =
                            NavigationGutterIconBuilder.create(AllIcons.Actions.Refresh).
                                    setTooltipText("Navigate to a comment");
                    result.add(getLineMarkerInfoForElem(element));
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
        final GutterIconNavigationHandler<PsiElement> navHandler = (e, elt) -> {
            // TODO: 16.12.18 add action implementation when haskell/kotlin support done
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


    private int getStringLine(@NotNull final String text,
                              @NotNull final String key) {
        String[] lines = text.split("\n");
        int lineNumber = 0;
        for (String line : lines) {
            if (line.contains(key))
                return lineNumber + 1;
            lineNumber++;
        }

        return -1;
    }

    private String getFullStringByLine(@NotNull final String text,
                                       final int lineNumber) {
        String[] lines = text.split("\n");
        return lines[lineNumber - 1].trim();
    }
}
