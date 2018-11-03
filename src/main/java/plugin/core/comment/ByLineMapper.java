package plugin.core.comment;

import lombok.Data;

import java.util.List;

@Data  public class ByLineMapper{
//    private List<CommentsMapper> comments;
    private List<Comment> comments;
    private long line;
    ByLineMapper(){

    }
}