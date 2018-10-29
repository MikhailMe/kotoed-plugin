package core.sumbission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

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
    //private List<Long> openSubmissions;

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "name = " + name + "\n" +
                "deleted = " + deleted + "\n" +
                "denizen = " + denizen.toString() + "\n" +
                "repoUrl = " + repoUrl + "\n" +
                "courseId = " + courseId + "\n" +
                "repoType = " + repoType;// + "\n" +
                //"openSubmissions = " + openSubmissions.toString();
    }
}
