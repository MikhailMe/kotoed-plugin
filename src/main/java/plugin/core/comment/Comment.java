package plugin.core.comment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Arrays;

@JsonDeserialize
@Data
public class Comment {

    private long id;

    private long datetime;

    private long submission_id;

    private long author_id;

    private String state;

    private String sourcefile;

    private long sourceline;

    private String text;

    private long original_submission_id;

    private long previous_comment_id;

    private long persistent_comment_id;

    private String  denizen_id;

    private OriginalCommentMapper original;

    public Comment() {

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***** Comment Details *****\n");
        sb.append("ID="+getId()+"\n");
        sb.append("Datetime="+getDatetime()+"\n");
        sb.append("Submission id="+getSubmission_id()+"\n");
        sb.append("Author id="+getAuthor_id()+"\n");
        sb.append("Source file="+getSourcefile()+"\n");
        sb.append("Source line="+getSourceline()+"\n");
        sb.append("**************Text***************"+"\n");
        sb.append(getText()+"\n");
        sb.append("*****************************"+"\n");

        return sb.toString();

    }

}
