package plugin.gui.Tabs;

import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class BuildTab extends JPanel {

    public JPanel panel;
    private JButton refreshButton;

    public BuildTab() {
        super();
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/refresh.png"));
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
        this.refreshButton.setIcon(new ImageIcon(newimg));
    }
}
