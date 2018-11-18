package plugin.gui.Stabs;

import javax.swing.*;

public class Comment{
    public String userName;
    public String date;
    public String text;
    public int lineNumber;
    public String fileName;

    public Comment(String userName, String date, String text, int lineNumber,String fileName) {
        this.userName = userName;
        this.date = date;
        this.text = text;
        this.lineNumber = lineNumber;
        this.fileName = fileName;
    }

}
