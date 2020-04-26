package site.alanliang.geekblog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 11:21
 * Version 1.0
 **/
@Data
public class ArticleDateVo {
    private Integer year;

    private Integer month;

    private Integer day;

    private Integer articleCount;

    private String date;
}
