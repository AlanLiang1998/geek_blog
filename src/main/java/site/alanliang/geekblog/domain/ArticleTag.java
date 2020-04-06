package site.alanliang.geekblog.domain;

import lombok.Data;

/**
 * @Descriptin 文章标签关系
 * @Author AlanLiang
 * Date 2020/4/5 20:59
 * Version 1.0
 **/
@Data
public class ArticleTag {
    private Long articleId;

    private Long tagId;
}
