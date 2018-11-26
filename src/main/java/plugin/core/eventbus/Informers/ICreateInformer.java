package plugin.core.eventbus.Informers;

import org.jetbrains.annotations.NotNull;
import plugin.core.util.ProjectRecord;
import plugin.core.util.SubmissionRecord;

public interface ICreateInformer {

    ProjectRecord createProject(@NotNull final String projectName,
                                final int denizenId,
                                final int courseId,
                                @NotNull final String repoType,
                                @NotNull final String repoUrl);

    SubmissionRecord createSubmission(final int projectId,
                                      @NotNull final String revision);

    /*void createComment(final int submissionId,
                       final int authorId,
                       final int sourceLine,
                       @NotNull final String sourceFile,
                       @NotNull final String text);*/
}
