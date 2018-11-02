package plugin.core.comment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class Comment {

    private long line;
    private CommentStructure commentStructure;

    public Comment() {

    }

}
