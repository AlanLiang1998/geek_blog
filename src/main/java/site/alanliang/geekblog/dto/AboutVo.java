package site.alanliang.geekblog.dto;

import lombok.Data;
import site.alanliang.geekblog.domain.Category;
import site.alanliang.geekblog.domain.Tag;
import site.alanliang.geekblog.vo.ArticleDateVo;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 15:05
 * Version 1.0
 **/
@Data
public class AboutVo {
    private Long articleCount;

    private Long categoryCount;

    private Long tagCount;

    private List<Category> categories;

    private List<Tag> tags;

    private List<ArticleDateVo> articleDates;
}
