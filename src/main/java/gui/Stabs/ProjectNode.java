package gui.Stabs;

public class ProjectNode {
    public String name;
    public String source;

    public ProjectNode(String name, String source) {
        this.name = name;
        this.source = source;
    }

    public String toString(){
        return name + " " + source;
    }
}
