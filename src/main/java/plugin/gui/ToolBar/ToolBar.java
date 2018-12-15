package plugin.gui.ToolBar;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import plugin.gui.Actions.*;

import javax.swing.*;

public class ToolBar {
    public static final String PLACE = "right";
    public static final boolean HORIZONTAL = false;

    public static ActionToolbar noUserToolBar(JComponent component) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new SignInAction());
        group.add(new SignUpAction());
        group.add(new AboutAction());

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }

    public static ActionToolbar createCommentToolbar(JComponent component) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new RefreshAction());

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }

    public static ActionToolbar createBuildToolbar(JComponent component) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new RefreshAction());

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }

    public static ActionToolbar createSubmissionToolbar(JComponent component) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new AutoSubmitAction());
        group.add(new RefreshAction());

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }
}
