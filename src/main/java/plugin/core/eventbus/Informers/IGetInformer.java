package plugin.core.eventbus.Informers;

import plugin.core.comment.Comment;
import plugin.core.course.Course;
import plugin.core.project.Project;
import plugin.core.sumbission.Submission;

import java.util.List;

public interface IGetInformer {

    List<Course> getCourses();

    List<Project> getProjects();

    List<Submission> getSubmissions(final int courseId, final int pageSize, final int currentPage);

    List<Comment> getComments(final int submissionNumber);
}
