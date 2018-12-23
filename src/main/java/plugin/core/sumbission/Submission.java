package plugin.core.sumbission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = "parent_submission_id")
public class Submission {

    long id;
    String state;
    long datetime;
    String revision;
    long projectId;

    public Submission() {

    }
}
