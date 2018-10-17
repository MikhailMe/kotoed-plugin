package core.parser;

import core.comment.Comment;
import core.sumbission.Submission;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static String getCookieFromHeaders(final String headers) {
        String prefix = "Set-Cookie: ";
        String postfix = ";";
        int beginIndex = headers.indexOf(prefix) + prefix.length();
        int endIndex = headers.indexOf(postfix);
        return headers.substring(beginIndex, endIndex);
    }

    // TODO: 10/17/18 write implementation
    public static List<Comment> getCommnets(String json) {

        JsonObject jsonObject = new JsonObject(json);

        return new ArrayList<>();
    }

    // TODO: 10/17/18 write implementation
    public static List<Submission> getSubmissions(String json) {

        JsonObject jsonObject = new JsonObject(json);

        return new ArrayList<>();
    }
}
