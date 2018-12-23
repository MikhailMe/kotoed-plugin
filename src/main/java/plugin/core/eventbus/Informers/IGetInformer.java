package plugin.core.eventbus.Informers;

import plugin.core.comment.Comment;
import plugin.core.course.Course;
import plugin.core.project.Project;
import plugin.core.sumbission.Submission;

import java.util.List;

public interface IGetInformer {

    List<Course> getCourses();

    List<Project> getProjects(final long courseId, final long pageSize, final long currentPage);

    List<Submission> getSubmissions(final long courseId, final long pageSize, final long currentPage);

    List<Comment> getComments(final long submissionNumber);
}
