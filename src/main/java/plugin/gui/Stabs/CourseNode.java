package plugin.gui.Stabs;

import org.jetbrains.annotations.NotNull;

public class CourseNode {

    public String name;
    public String fileIcon;
    public String status;

    public CourseNode(@NotNull final String name,
                      @NotNull final String fileIcon,
                      @NotNull final String status) {
        this.name = name;
        this.fileIcon = fileIcon;
        this.status = status;
    }

    public String toString() {
        return name + " " + fileIcon;
    }
}
