package plugin.gui.SignInWindow;

import org.fest.swing.core.ComponentMatcher;

import java.awt.*;
import javax.swing.*;

public class CaptionMatcher implements ComponentMatcher {
    private String expectedCaption;
    private String actualComponentCaption;

    public CaptionMatcher(String expectedCaption) {
        this.expectedCaption = expectedCaption;
    }

    //SignInWindow sg = new SignInWindow()

    @Override
    public boolean matches(Component comp) {
        if (comp != null && expectedCaption != null && comp instanceof JButton) {
            actualComponentCaption = ((JButton) comp).getText();
            if (expectedCaption.equals(actualComponentCaption)) {
                return true;
            }
        }
        return false;
    }

    public String getExpectedCaption() {
        return expectedCaption;
    }

    public void setExpectedCaption(String expectedCaption) {
        this.expectedCaption = expectedCaption;
    }

}

