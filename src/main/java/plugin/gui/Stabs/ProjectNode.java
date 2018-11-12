package gui.Stabs;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ProjectNode {

    private String name;
    private String source;

    public ProjectNode(@NotNull final String name,
                       @NotNull final String source) {
        this.name = name;
        this.source = source;
    }

    public String toString(){
        return name + " " + source;
    }
}
