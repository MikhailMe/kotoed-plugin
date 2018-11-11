package gui.Stabs;

import javax.swing.*;

public class Comment{
    public String userName;
    public String date;
    public String text;
    public int lineNumber;

    public Comment(String userName, String date, String text, int lineNumber) {
        this.userName = userName;
        this.date = date;
        this.text = text;
        this.lineNumber = lineNumber;
    }

}
