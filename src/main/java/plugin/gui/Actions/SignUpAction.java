package plugin.gui.Actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import plugin.gui.Items.SignUpWindow;

import javax.swing.*;

public class SignUpAction extends DumbAwareAction {
    private static final String TEXT = "Sign Up";
    private static final String DESCRIPTION = "Sign Up to KotOED server";

    public SignUpAction()
    {
        super(TEXT,DESCRIPTION, AllIcons.General.ZoomIn);
    }
    @Override
    public void actionPerformed(AnActionEvent e) {
        new SignUpWindow();
    }
}
