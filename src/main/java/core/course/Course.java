package core.course;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = { "icon" , "document"})
public class Course {

    public long id;
    public String name;
    public String state;
    public String base_repo_url;
    public String base_revision;
    public long build_template_id;

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "name = " + name + "\n" +
                "state = " + state + "\n" +
                "base_repo_url = " + base_repo_url + "\n" +
                "base_revision = " + base_revision + "\n" +
                "build_template_id = " + build_template_id + "\n";
    }
}
