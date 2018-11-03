package gui.Stabs;

public class SubmissionNode {
    public String text;
    public int number;
    public boolean status;

    public SubmissionNode(String text, int number, boolean status) {
        this.text = text;
        this.number = number;
        this.status = status;
    }

    public String toString(){
        return text ;
    }
}
