package plugin.core.comment;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = "original")
public class Comment {

    private long id;
    private long datetime;
    private long submissionId;
    private long authorId;
    private String state;
    private String sourcefile;
    private long sourceline;
    private String text;
    private long originalSubmissionId;
    private long previousCommentId;
    private long persistentCommentId;
    private String denizenId;

    public Comment() {
    }

    @Override
    public String toString() {
        return "***** Comment Details *****\n" +
                "ID = " + getId() + "\n" +
                "Datetime = " + getDatetime() + "\n" +
                "Submission id = " + getSubmissionId() + "\n" +
                "Author id = " + getAuthorId() + "\n" +
                "Source file = " + getSourcefile() + "\n" +
                "Source line = " + getSourceline() + "\n" +
                "**************Text***************" + "\n" +
                getText() + "\n" +
                "*****************************" + "\n";
    }

}
