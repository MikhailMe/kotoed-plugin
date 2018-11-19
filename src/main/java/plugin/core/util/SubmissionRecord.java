package plugin.core.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = "verification_data")
public class SubmissionRecord {

    Record record;

    SubmissionRecord() {

    }

    @Override
    public String toString() {
        return record.toString();
    }

    @Data
    @JsonDeserialize
    class Record {
        private long id;
        private long datetime;
        private long projectId;
        private String state;
        private String revision;

        public Record() {

        }

        @Override
        public String toString() {
            return "id = " + id + "\n" +
                    "datetime = " + datetime + "\n" +
                    "projectId = " + projectId + "\n" +
                    "state = " + state + "\n" +
                    "revision = " + revision + "\n";
        }
    }
}
