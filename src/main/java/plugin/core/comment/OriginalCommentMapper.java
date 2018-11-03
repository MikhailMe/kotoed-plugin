package plugin.core.comment;

import lombok.Data;

@Data public class OriginalCommentMapper{

    private long id;
    private long datetime;
    private long submission_id;
    private long author_id;
    private String state;
    private String  sourcefile;
    private long  sourceline;
    private String  text;
    private long  original_submission_id;
    private long previous_comment_id;
    private long  persistent_comment_id;
    OriginalCommentMapper(){

    }
}
