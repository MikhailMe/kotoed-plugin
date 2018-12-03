package plugin.gui.Utils;

import lombok.Data;
import plugin.core.comment.Comment;

import java.util.List;

@Data
public class CommentTreeItem {
    private String sourcefile;
    private long sourceline;
    private List<Comment> commentList;

    public CommentTreeItem(String sourcefile, long sourceline, List<Comment> commentList) {
        this.sourcefile = sourcefile;
        this.sourceline = sourceline;
        this.commentList = commentList;
    }

}
