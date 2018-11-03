package plugin.core.comment;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;




@Data class CommentsMapper{

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

    CommentsMapper(){}
}


@Data
public class CommentsJsonMapper {

    private List<ByFileMapper> by_file = new ArrayList<ByFileMapper>();
    private  List<String> lost = new ArrayList <String> ();

    public CommentsJsonMapper(){

    }

    public List<Comment> getCommentsFromJson(){
        ArrayList<Comment> allComments = new ArrayList<>();

        for (ByFileMapper byFile:
             by_file) {
            for (ByLineMapper byLine:
                 byFile.getBy_line()) {
                allComments.addAll(byLine.getComments());
            }
        }
        return allComments;
    }
}
