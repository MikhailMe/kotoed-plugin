package plugin.gui.Tabs;

import lombok.Data;

import javax.swing.*;
import java.awt.*;

import static plugin.gui.Utils.Strings.ICON_SIZE;
import static plugin.gui.Utils.Strings.REFRESH_ICON;

@Data
public class BuildTab extends JPanel {

    public JPanel panel;
    private JButton refreshButton;

    public BuildTab() {
        super();
        this.refreshButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(REFRESH_ICON)).getImage().getScaledInstance( ICON_SIZE, ICON_SIZE,  java.awt.Image.SCALE_SMOOTH )));
    }
}
