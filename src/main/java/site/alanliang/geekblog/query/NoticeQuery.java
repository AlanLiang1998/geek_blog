package site.alanliang.geekblog.query;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeQuery {
    private String title;

    private String content;

    private Date startDate;

    private Date endDate;

    private Boolean display;
}
