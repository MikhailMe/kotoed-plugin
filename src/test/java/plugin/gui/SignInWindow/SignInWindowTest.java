package plugin.gui.SignInWindow;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.junit.BeforeClass;
import plugin.gui.Items.SignInWindow;
import org.junit.Test;
import plugin.core.rest.Sender;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.RandomStringUtils;


public class SignInWindowTest {

//    @BeforeClass
//    public static void setUpOnce() throws Exception {
//
//
//    }

    @Test
    public void SignForm() {
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        ComponentFinder finder = robot.finder();
        finder.find(new CaptionMatcher("+"));
    }
}
