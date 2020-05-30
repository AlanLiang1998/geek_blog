package site.alanliang.geekblog.vo;

import lombok.Data;
import site.alanliang.geekblog.model.Category;
import site.alanliang.geekblog.model.Photo;
import site.alanliang.geekblog.model.Tag;

import java.io.Serializable;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 15:05
 * Version 1.0
 **/
@Data
public class AboutVO implements Serializable {
    private Long articleCount;

    private Long categoryCount;

    private Long tagCount;

    private List<Category> categories;

    private List<Tag> tags;

    private List<ArticleDateVO> articleDates;

    private List<Photo> photos;
}
