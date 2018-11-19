package plugin.core.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import org.jetbrains.annotations.NotNull;
import plugin.core.util.CommentRecord;
import plugin.core.util.ProjectRecord;
import plugin.core.util.SubmissionRecord;

import java.io.IOException;

// FIXME: 11/19/2018 rewrite with generics

public class CreateParser extends BaseParser {

    public static SubmissionRecord parseCreateSubmission(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<SubmissionRecord>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ProjectRecord parseCreateProject(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<ProjectRecord>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CommentRecord parseCreateComment(@NotNull final String json) {
        try {
            return mapper.readValue(json, new TypeReference<CommentRecord>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
