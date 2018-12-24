package plugin.gui.Actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import plugin.gui.Items.About;

public class SignOutAction extends DumbAwareAction {
    private static final String TEXT = "Sign Out";
    private static final String DESCRIPTION = "Sign Out";


    public SignOutAction()
    {
        // TODO: 24.12.2018 change icon
        super(TEXT,DESCRIPTION, AllIcons.Actions.Exit);
    }
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: 24.12.2018 add implementation
    }
}
