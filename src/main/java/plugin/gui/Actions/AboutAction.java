package plugin.gui.Actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import plugin.gui.Items.About;

public class AboutAction extends DumbAwareAction {
    private static final String TEXT = "About";
    private static final String DESCRIPTION = "Info about plugin";


    public AboutAction()
    {
        super(TEXT,DESCRIPTION, AllIcons.Actions.Help);
    }
    @Override
    public void actionPerformed(AnActionEvent e) {
        new About();
    }
}
