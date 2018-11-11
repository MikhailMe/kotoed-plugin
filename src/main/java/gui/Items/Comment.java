package gui.Items;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Comment extends JPanel{
    private JPanel panel1;
    private JTextArea textArea;
    private Color color = Color.WHITE;

    private Color oldColor;
    public Comment(gui.Stabs.Comment comment){
        super();
        textArea.setText(comment.text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel1.setPreferredSize(new Dimension(470,100));
        TitledBorder title = BorderFactory.createTitledBorder(comment.userName + " @ " + comment.date + " at line:" + comment.lineNumber);
        panel1.setBorder(title);
        textArea.setEditable(false);
        this.add(panel1);

        this.setBackground(color);

        this.addMouseListener(new MouseAdapter() {
            /*@Override
            public void mouseEntered(MouseEvent e) {
                oldColor = panel1.getBackground();
                panel1.setBackground(Color.LIGHT_GRAY);
                textArea.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel1.setBackground(oldColor);
                textArea.setBackground(oldColor);
            }*/
        });
        textArea.addMouseListener(new MouseAdapter() {
            /*@Override
            public void mouseEntered(MouseEvent e) {
                oldColor = textArea.getBackground();
                textArea.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                textArea.setBackground(oldColor);
            }*/
        });

        this.setVisible(true);

    }
}
