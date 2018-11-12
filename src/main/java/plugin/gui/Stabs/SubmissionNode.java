package plugin.gui.Stabs;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class SubmissionNode {

    private String text;
    private int number;
    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public SubmissionNode(@NotNull final String text,
                          final int number,
                          boolean status) {
        this.text = text;
        this.number = number;
        this.status = status;
    }

    public String toString(){
        return text ;
    }
}
