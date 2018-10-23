package core.course;

import lombok.Data;

@Data
public class Course {

    private long id;

    private String icon;

    private String state;

    private String document;

    private String baseRepoURL;

    private String baseRevision;

    private String buildTemplateId;

}
