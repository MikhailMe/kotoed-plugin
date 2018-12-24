package plugin.core.comment;

import org.junit.Test;
import plugin.core.comment.Comment;
import plugin.core.comment.CommentsJsonMapper;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentTest {

    @Test
    public void getNormalDatetimeTest() {
        Date date = new Date(1545638744620L); // Mon Dec 24 08:05:44 UTC 2018
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        assert format.format(date).equals("24/12/2018 08:05:44");
    }

    @Test
    public void getCommentsFromJsonTest() {
        CommentsJsonMapper commentsJsonMapper = new CommentsJsonMapper();
        List<Comment> allComments = commentsJsonMapper.getCommentsFromJson();

        assert !allComments.isEmpty();
    }
}
