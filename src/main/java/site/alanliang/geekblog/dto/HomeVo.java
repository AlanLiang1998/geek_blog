package site.alanliang.geekblog.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import site.alanliang.geekblog.domain.Article;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 13:01
 * Version 1.0
 **/
@Data
public class HomeVo {
    private List<Article> topArticles;

    private List<Article> recommendArticles;

    private Page<Article> pageInfo;
}
