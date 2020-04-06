package site.alanliang.geekblog.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Descriptin 文章评论
 * @Author AlanLiang
 * Date 2020/4/5 21:01
 * Version 1.0
 **/
@Data
public class Comment {
    private Long id;

    private String name;

    private Date createTime;
}
