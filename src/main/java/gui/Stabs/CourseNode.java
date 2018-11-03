package gui.Stabs;

public class CourseNode {
    public String name;
    public String fileIcon;
    public String status;

    public CourseNode(String name, String fileIcon, String status) {
        this.name = name;
        this.fileIcon = fileIcon;
        this.status = status;
    }

    public String toString(){
        return name + " " + fileIcon;
    }
}
