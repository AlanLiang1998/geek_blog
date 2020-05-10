package site.alanliang.geekblog.vo;

import lombok.Data;
import site.alanliang.geekblog.model.Article;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 16:47
 * Version 1.0
 **/
@Data
public class ArticleDetailVO {
    private Article article;

    private Article prevPreview;

    private Article nextPreview;
}
