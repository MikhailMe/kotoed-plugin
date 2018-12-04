package plugin.core.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import plugin.core.comment.Comment;
import plugin.core.comment.CommentsJsonMapper;
import plugin.core.course.Course;
import plugin.core.project.Project;
import plugin.core.sumbission.Submission;

import java.io.IOException;
import java.util.List;

// FIXME: 11/19/2018 rewrite with generics

public class GetParser extends BaseParser {

    public static String parseCookieFromHeaders(@NotNull final String headers) {
        final String prefix = "Set-Cookie: ";
        final String postfix = ";";
        int beginIndex = headers.indexOf(prefix) + prefix.length();
        int endIndex = headers.indexOf(postfix);
        return headers.substring(beginIndex, endIndex);
    }

    public static List<Course> parseCourses(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Course>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Project> parseProjects(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Project>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Comment> parseComments(@NotNull final String json) {
        try {
            // TODO: 11/03/2018 handle situation with error getting comment and empty comments
            CommentsJsonMapper commentsMapper = mapper.readValue(json, CommentsJsonMapper.class);
            return commentsMapper.getCommentsFromJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Submission> parseSubmissions(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Submission>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // FIXME: 12/4/2018 FIXME
    public static long getDenizenId(@NotNull String whoAmI) {
        JsonObject jo = new Gson().fromJson(whoAmI, JsonObject.class);
        return jo.get("id").getAsLong();
    }

}
