package site.alanliang.geekblog.dto;

import lombok.Data;
import site.alanliang.geekblog.domain.Article;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 16:47
 * Version 1.0
 **/
@Data
public class ArticleDetailVo {
    private Article article;

    private Article prevPreview;

    private Article nextPreview;
}
