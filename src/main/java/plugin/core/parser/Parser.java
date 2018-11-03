package plugin.core.parser;

import plugin.core.comment.CommentsJsonMapper;
import plugin.core.course.Course;
import plugin.core.comment.Comment;
import plugin.core.project.Project;
import plugin.core.sumbission.Submission;
import org.jetbrains.annotations.NotNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Parser {

    private static final ObjectMapper mapper;

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

    public static List<Course> getCourses(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Course>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Project> getProjects(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Project>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Comment> getComments(@NotNull final String json) {
        try {


// TODO: 11/03/2018 handle situation with error getting comment and empty comments
            CommentsJsonMapper commentsMapper = mapper.readValue(json, CommentsJsonMapper.class);
            return commentsMapper.getCommentsFromJson();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Submission> getSubmissions(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Submission>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
