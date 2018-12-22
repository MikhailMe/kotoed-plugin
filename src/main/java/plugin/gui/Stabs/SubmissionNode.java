package plugin.gui.Stabs;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class SubmissionNode {

    private String text;
    private long number;
    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public SubmissionNode(@NotNull final String text,
                          final long number,
                          boolean status) {
        this.text = text;
        this.number = number;
        this.status = status;
    }

    public String toString() {
        return text;
    }
}
