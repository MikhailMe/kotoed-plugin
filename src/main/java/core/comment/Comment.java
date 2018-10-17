package core.comment;

import lombok.Data;

@Data
public class Comment {

    private long line;

    private CommentStructure commentStructure;

}
