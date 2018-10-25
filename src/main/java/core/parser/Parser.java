package core.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import core.comment.Comment;
import core.course.Course;
import core.sumbission.Submission;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class Parser {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public static String getCookieFromHeaders(@NotNull final String headers) {
        String prefix = "Set-Cookie: ";
        String postfix = ";";
        int beginIndex = headers.indexOf(prefix) + prefix.length();
        int endIndex = headers.indexOf(postfix);
        return headers.substring(beginIndex, endIndex);
    }

    public static List<Course> getCourses(@NotNull final String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Course>>() {
        });
    }

    // TODO: 10/17/18 fix this implementation, cause it may more difficulty
    public static List<Comment> getComments(@NotNull final String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Comment>>() {
        });
    }

    // TODO: 10/25/2018 check it out
    public static List<Submission> getSubmissions(@NotNull final String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Submission>>() {
        });
    }
}
