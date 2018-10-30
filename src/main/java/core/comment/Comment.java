package core.comment;

import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class Comment {

    private long line;
    private CommentStructure commentStructure;

    public Comment() {

    }

}
