package plugin.core.util;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

// FIXME: 11/19/2018 write me

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = "")
public class CommentRecord {

    @Data
    @JsonDeserialize
    class Record {
        private long id;


        public Record() {

        }

        @Override
        public String toString() {
            return "id = " + id + "\n";
        }
    }
}
