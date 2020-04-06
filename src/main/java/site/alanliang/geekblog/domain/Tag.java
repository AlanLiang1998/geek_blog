package site.alanliang.geekblog.domain;

import lombok.Data;

/**
 * @Descriptin 文章标签
 * @Author AlanLiang
 * Date 2020/4/5 21:02
 * Version 1.0
 **/
@Data
public class Tag {
    private Long id;

    private String name;

    private String createTime;
}
