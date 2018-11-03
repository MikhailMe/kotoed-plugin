package plugin.core.comment;


import lombok.Data;

import java.util.List;

@Data
public class ByFileMapper{

    private List<ByLineMapper> by_line;
    private String filename;
    public ByFileMapper(){

    }

}
