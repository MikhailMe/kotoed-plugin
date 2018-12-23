package plugin.gui.Stabs;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class SubmissionNode {

    private long number;
    private String status;

    public SubmissionNode(final long number,
                          @NotNull String status) {
        this.number = number;
        this.status = status;
    }

}
