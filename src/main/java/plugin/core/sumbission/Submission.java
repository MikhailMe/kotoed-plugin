package plugin.core.sumbission;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = {"document", "denizen_id", "openSubmissions"})
public class Submission {

    private long id;
    private String name;
    private boolean deleted;
    private Denizen denizen;
    private String repoUrl;
    private long courseId;
    private String repoType;

    public Submission() {

    }

    @Override
    public String toString() {
        return "projectId = " + id + "\n" +
                "name = " + name + "\n" +
                "deleted = " + deleted + "\n" +
                "denizen = " + denizen.toString() + "\n" +
                "repoUrl = " + repoUrl + "\n" +
                "courseId = " + courseId + "\n" +
                "repoType = " + repoType + "\n";
    }
}
