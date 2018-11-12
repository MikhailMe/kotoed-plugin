package core.sumbission;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
