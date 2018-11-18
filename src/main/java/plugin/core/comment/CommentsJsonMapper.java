package plugin.core.comment;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Data
public class CommentsJsonMapper {

    private List<String> lost;
    private List<ByFileMapper> byFile;

    {
        lost = new ArrayList<>();
        byFile = new ArrayList<>();
    }

    public CommentsJsonMapper() {
    }

    public List<Comment> getCommentsFromJson() {
        List<Comment> allComments = new ArrayList<>();
        for (ByFileMapper byFile : byFile)
            for (ByLineMapper byLine : byFile.getByLine())
                allComments.addAll(byLine.getComments());
        return allComments;
    }
}
