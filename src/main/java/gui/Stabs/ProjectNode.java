package gui.Stabs;

import org.jetbrains.annotations.NotNull;

public class ProjectNode {

    public String name;
    public String source;

    public ProjectNode(@NotNull final String name,
                       @NotNull final String source) {
        this.name = name;
        this.source = source;
    }

    public String toString(){
        return name + " " + source;
    }
}
