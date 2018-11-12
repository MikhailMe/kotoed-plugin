package gui.Stabs;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CourseNode {

    private String name;
    private String fileIcon;
    private String status;

    public CourseNode(@NotNull final String name,
                      @NotNull final String fileIcon,
                      @NotNull final String status) {
        this.name = name;
        this.fileIcon = fileIcon;
        this.status = status;
    }

    public String toString(){
        return name + " " + fileIcon;
    }
}
