package plugin.core.sender;

import org.junit.Test;
import plugin.core.rest.Sender;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.RandomStringUtils;

public class SenderTest {

    private Sender sender;

    private void initialize() {
        sender = new Sender("GLOBAL");
    }

    @Test
    public void singInTest() {
        initialize();

        String denizen1 = "alewa";
        String password1 = "alewa";
        String cookie1 = singInHelper(denizen1, password1);
        assert !cookie1.isEmpty();

        String denizen2 = "qaz";
        String password2 = "qaz";
        String cookie2 = singInHelper(denizen2, password2);
        assert !cookie2.isEmpty();
    }

    private String singInHelper(@NotNull final String denizen,
                                @NotNull final String password) {
        initialize();
        return sender.signIn(denizen, password);
    }

    @Test
    public void singUpTest() {
        initialize();
        String denizen = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(15);
        String response = sender.signUp(denizen, password);
        assert sender.isSuccessSignUp(response);
        String cookie = singInHelper(denizen, password);
        assert !cookie.isEmpty();
    }

    @Test
    public void isSignUpTest() {
        initialize();

        String test1 = "alewa";
        String response1 = sender.signUp(test1, test1);
        assert !sender.isSuccessSignUp(response1);

        String test2 = "qaz";
        String response2 = sender.signUp(test2, test2);
        assert !sender.isSuccessSignUp(response2);

    }
}
