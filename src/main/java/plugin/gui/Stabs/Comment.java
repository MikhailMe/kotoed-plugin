package plugin.gui.Stabs;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class Comment {

    private String userName;
    private String date;
    private String text;
    private int lineNumber;
    private String fileName;

    public Comment(@NotNull final String userName,
                   @NotNull final String date,
                   @NotNull final String text,
                   final int lineNumber,
                   @NotNull final String fileName) {
        this.userName = userName;
        this.date = date;
        this.text = text;
        this.lineNumber = lineNumber;
        this.fileName = fileName;
    }

}
