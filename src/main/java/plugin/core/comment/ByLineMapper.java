package plugin.core.comment;

import lombok.Data;

import java.util.List;

@Data
class ByLineMapper {

    private long line;
    private List<Comment> comments;

    ByLineMapper() {
    }
}
