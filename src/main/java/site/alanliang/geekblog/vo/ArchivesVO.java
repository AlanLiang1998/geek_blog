package site.alanliang.geekblog.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import site.alanliang.geekblog.model.Article;

import java.util.List;


/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 15:44
 * Version 1.0
 **/
@Data
public class ArchivesVO {
    private List<ArticleDateVO> articleDates;

    private Page<Article> pageInfo;
}
