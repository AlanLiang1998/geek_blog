package site.alanliang.geekblog.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Descriptin 文章
 * @Author AlanLiang
 * Date 2020/4/5 20:53
 * Version 1.0
 **/
@Data
public class Article {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private String textContent;

    private String cover;

    private Integer views;

    private Integer likes;

    private Integer comments;

    private Boolean appreciable;
    
    private Boolean commentable;
    
    private Boolean published;

    private Long authorId;

    private Long categoryId;

    private Date createTime;

    private Date updateTime;
}
