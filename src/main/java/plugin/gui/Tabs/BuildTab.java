package plugin.gui.Tabs;

import lombok.Data;

import javax.swing.*;

import static java.awt.Image.SCALE_SMOOTH;
import static plugin.gui.Utils.Strings.ICON_SIZE;
import static plugin.gui.Utils.Strings.REFRESH_ICON;

@Data
public class BuildTab extends JPanel {

    public JPanel panel;
    private JButton refreshButton;

    public BuildTab() {
        super();
    }
}
