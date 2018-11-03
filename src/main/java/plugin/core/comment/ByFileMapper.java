package plugin.core.comment;

import lombok.Data;

import java.util.List;

@Data
public class ByFileMapper {

    private String filename;
    private List<ByLineMapper> byLine;

    public ByFileMapper() {
    }
}
