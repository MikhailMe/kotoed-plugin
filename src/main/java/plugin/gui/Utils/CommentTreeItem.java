package plugin.gui.Utils;

import lombok.Data;
import plugin.core.comment.Comment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class CommentTreeItem {

    private long sourceline;
    private String sourcefile;
    private List<Comment> commentList;

    public CommentTreeItem(final long sourceline,
                           @NotNull final String sourcefile,
                           @NotNull List<Comment> commentList) {
        this.sourcefile = sourcefile;
        this.sourceline = sourceline;
        this.commentList = commentList;
    }

}
