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
        String cookie1 = singInTest(denizen1, password1);
        assert !cookie1.isEmpty();

        String denizen2 = "qaz";
        String password2 = "qaz";
        String cookie2 = singInTest(denizen2, password2);
        if (cookie2.isEmpty()) throw new AssertionError();
    }

    private String singInTest(@NotNull final String denizen,
                             @NotNull final String password) {
        initialize();
        return sender.signIn(denizen, password);
    }

    @Test
    public void singUpTest() {
        initialize();
        String denizen = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(15);
        sender.signUp(denizen, password);
        String cookie = singInTest(denizen, password);
        assert !cookie.isEmpty();
    }
}
