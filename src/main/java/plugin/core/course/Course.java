package plugin.core.course;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = {"icon", "document"})
public class Course {

    private long id;
    private String name;
    private String state;
    private String baseRepoUrl;
    private String baseRevision;
    private long buildTemplateId;

    public Course(long id,
                  String name,
                  String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "name = " + name + "\n" +
                "state = " + state + "\n" +
                "baseRepoUrl = " + baseRepoUrl + "\n" +
                "baseRevision = " + baseRevision + "\n" +
                "buildTemplateId = " + buildTemplateId + "\n";
    }
}
