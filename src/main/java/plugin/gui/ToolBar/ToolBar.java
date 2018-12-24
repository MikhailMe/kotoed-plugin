package plugin.gui.ToolBar;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;
import plugin.gui.Actions.*;

import javax.swing.*;
import java.util.function.Function;

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

    public static ActionToolbar createCustomToolbar(@NotNull JComponent component,
                                                    @NotNull ToolWindow toolWindow,
                                                    @NotNull String componentString) {
        DefaultActionGroup group = new DefaultActionGroup();
        switch (componentString) {
            case "Comment": {
                group.add(new RefreshAction());
                group.add(new SignOutAction(toolWindow));
                break;
            }
            case "Build": {
                group.add(new RefreshAction());
                group.add(new SignOutAction(toolWindow));
                break;
            }
            case "Submission": {
                group.add(new AutoSubmitAction());
                group.add(new RefreshAction());
                group.add(new SignOutAction(toolWindow));
                break;
            }
        }

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }

    /*public static ActionToolbar createCommentToolbar(JComponent component, ToolWindow toolWindow) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new RefreshAction());
        group.add(new SignOutAction(toolWindow));

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }

    public static ActionToolbar createBuildToolbar(JComponent component, ToolWindow toolWindow) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new RefreshAction());
        group.add(new SignOutAction(toolWindow));

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }

    public static ActionToolbar createSubmissionToolbar(JComponent component, ToolWindow toolWindow) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new AutoSubmitAction());
        group.add(new RefreshAction());
        group.add(new SignOutAction(toolWindow));

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(PLACE, group, HORIZONTAL);
        toolbar.setTargetComponent(component);

        return toolbar;
    }*/
}
