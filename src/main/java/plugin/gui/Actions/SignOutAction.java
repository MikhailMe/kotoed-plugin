package plugin.gui.Actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import plugin.gui.KotoedContext;
import plugin.gui.ToolBar.ToolBar;
import plugin.gui.Utils.PsiKeys;

import javax.swing.*;

import static plugin.gui.Utils.Strings.AUTH;

public class SignOutAction extends DumbAwareAction {

    private ToolWindow toolWindow;
    private static final String TEXT = "Sign Out";
    private static final String DESCRIPTION = "Sign Out";

    public SignOutAction(ToolWindow toolWindow) {
        // TODO: 24.12.2018 change icon
        super(TEXT, DESCRIPTION, AllIcons.Actions.Exit);
        this.toolWindow = toolWindow;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        int click = JOptionPane.showConfirmDialog(
                null,
                "Do you really want to log out ?",
                "Logout",
                JOptionPane.YES_NO_OPTION);
        if (click != JOptionPane.NO_OPTION) {

            toolWindow.getContentManager().removeAllContents(true);

            ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

            JPanel panel = new JPanel();
            SimpleToolWindowPanel noUserWindow = new SimpleToolWindowPanel(false, true);
            noUserWindow.setContent(panel);
            noUserWindow.setToolbar(ToolBar.noUserToolBar(panel).getComponent());

            Content user = contentFactory.createContent(noUserWindow, AUTH, false);
            toolWindow.getContentManager().addContent(user);

            KotoedContext.initTabs();
            PsiKeys.clearKeys(KotoedContext.project);
        }
    }
}
