package site.alanliang.geekblog.query;

import lombok.Data;

@Data
public class NoticeQuery {
    private String title;

    private String content;

    private String startDate;

    private String endDate;

    private Boolean display;
}
