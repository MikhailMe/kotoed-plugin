package plugin.gui.Actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import plugin.gui.Items.SignInWindow;

public class SignInAction extends DumbAwareAction {
    private static final String TEXT = "Sign In";
    private static final String DESCRIPTION = "Sign In to KotOED server";

    public SignInAction() {
        super(TEXT, DESCRIPTION, AllIcons.Actions.Exit);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        new SignInWindow();
    }
}
