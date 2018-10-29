package core.comment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize
public class Comment {

    private long line;
    private CommentStructure commentStructure;

    public Comment() {

    }

}
