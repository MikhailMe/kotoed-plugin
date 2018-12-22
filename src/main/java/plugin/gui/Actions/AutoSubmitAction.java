package plugin.gui.Actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

import javax.swing.*;

public class AutoSubmitAction  extends DumbAwareAction {
    private static final String TEXT = "AutoSubmit";
    private static final String DESCRIPTION = "Make AutoSubmit";

    public AutoSubmitAction(){
        super(TEXT,DESCRIPTION, AllIcons.Actions.Upload);
    }
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: 09.12.18 Complete AutoSubmit action stub
        JOptionPane.showMessageDialog(null,
                "Your message goes here",
                "AutoSubmit action stub",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
