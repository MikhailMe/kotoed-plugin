package plugin.core.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;


@Data
@JsonDeserialize
@JsonIgnoreProperties(value = "verification_data")
public class ProjectRecord {

    Record record;

    ProjectRecord() {

    }

    @Override
    public String toString() {
        return record.toString();
    }

    @Data
    @JsonDeserialize
    class Record {
        private long id;
        private long denizenId;
        private long courseId;
        private String repoType;
        private String repoUrl;
        private String name;
        private String deleted;

        public Record() {

        }

        @Override
        public String toString() {
            return "id = " + id + "\n" +
                    "denizenId = " + denizenId + "\n" +
                    "courseId = " + courseId + "\n" +
                    "repoType = " + repoType + "\n" +
                    "repoUrl = " + repoUrl + "\n" +
                    "name = " + name + "\n" +
                    "deleted = " + deleted + "\n";
        }
    }
}
