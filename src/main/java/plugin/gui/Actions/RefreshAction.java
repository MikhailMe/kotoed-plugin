package plugin.gui.Actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

import javax.swing.*;

public class RefreshAction extends DumbAwareAction {
    private static final String TEXT = "Refresh";
    private static final String DESCRIPTION = "Sync with server";

    public RefreshAction()
    {
        super(TEXT,DESCRIPTION, AllIcons.Actions.Refresh);
    }
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: 09.12.18 Complete refresh action stub
        JOptionPane.showMessageDialog(null,
                "Your message goes here",
                "Refresh action stub",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
