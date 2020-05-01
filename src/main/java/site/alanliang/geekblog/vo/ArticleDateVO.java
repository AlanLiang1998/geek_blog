package site.alanliang.geekblog.vo;

import lombok.Data;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 11:21
 * Version 1.0
 **/
@Data
public class ArticleDateVO {
    private Integer year;

    private Integer month;

    private Integer day;

    private Integer articleCount;

    private String date;
}
