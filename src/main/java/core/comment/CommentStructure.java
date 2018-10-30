package core.comment;

import lombok.Data;

@Data
public class CommentStructure {

    private long id;

    private long timestamp;

    private long submissionId;

    private long authorId;

    private String state;

    private String sourceFile;

    private long sourceLine;

    private String text;

    private long originalSubmissionId;

    private long persistentCommentId;

    private long previousCommentId;

    private String denizenId;

    public CommentStructure() {

    }

}
