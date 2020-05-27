package site.alanliang.geekblog.vo;

import lombok.Data;
import site.alanliang.geekblog.model.*;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexVO implements Serializable {
    private Long articleCount;

    private Long categoryCount;

    private Long tagCount;

    private Integer commentCount;

    private Integer userCount;

    private Integer visitorCount;

    private Integer viewCount;

    private Integer messageCount;

    private List<AccessLog> accessLogs;

    private List<OperationLog> operationLogs;

    private List<Comment> comments;

    private List<Message> messages;

    private List<Article> articles;

    private List<ViewDateVO> frontViews;

    private List<ViewDateVO> backViews;
}
