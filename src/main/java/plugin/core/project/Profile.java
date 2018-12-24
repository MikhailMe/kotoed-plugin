package plugin.core.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize
@JsonIgnoreProperties(value = "denizen_id")
public class Profile {

    private long id;
    private String groupId;
    private String lastName;
    private String firstName;
    private boolean emailNotifications;

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "groupId = " + groupId + "\n" +
                "lastName = " + lastName + "\n" +
                "firstName = " + firstName + "\n" +
                "emailNotifications = " + emailNotifications;
    }

}
