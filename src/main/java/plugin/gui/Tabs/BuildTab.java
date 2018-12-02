package plugin.gui.Tabs;

import lombok.Data;

import javax.swing.*;

@Data
public class BuildTab extends JPanel {

    public JPanel panel;
    private JButton button1;
    private JButton button2;

    public BuildTab() {
        super();
    }
}
