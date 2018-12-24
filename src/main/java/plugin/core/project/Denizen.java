package plugin.core.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
@JsonDeserialize
public class Denizen {

    private long id;
    private String email;
    private List<Profile> profiles;
    private String denizenId;

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "email = " + email + "\n" +
                "profiles = " + profiles.toString() + "\n" +
                "denizenId = " + denizenId;
    }

}
