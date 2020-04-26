package site.alanliang.geekblog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.domain.Tag;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/8 8:57
 * Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleVo extends Article {
    @Size(min = 1,message = "请选择至少一个标签")
    private List<Long> tagIdList;
}
