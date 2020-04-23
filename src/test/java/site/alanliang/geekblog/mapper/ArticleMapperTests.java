package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.vo.ArticleDateVO;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/16 18:32
 * Version 1.0
 **/
@SpringBootTest
public class ArticleMapperTests {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void selectPrevArticlePreview() {
        Article article = articleMapper.selectPrevArticlePreview(7L);
        System.out.println(article);
    }

    @Test
    void selectNextArticlePreview() {
        Article article = articleMapper.selectNextArticlePreview(1040L);
        System.out.println(article);
    }

    @Test
    void listPageArticlePreviewByCategoryId() {
        Page<Article> articlePage = new Page<>(1, 3);
        Page<Article> pageInfo = articleMapper.listPageArticlePreviewByCategoryId(articlePage, 1L);
        System.out.println(pageInfo);
    }

    @Test
    void listPageArticlePreviewByTagId() {
        Page<Article> articlePage = new Page<>(1, 3);
        Page<Article> pageInfo = articleMapper.listPageArticlePreviewByTagId(articlePage, 1L);
        System.out.println(pageInfo);
    }

    @Test
    void countArticleByDate() {
        Integer type = Constant.FILTER_BY_MONTH;
        List<ArticleDateVO> articleDates = articleMapper.countArticleByDate(type);
        System.out.println(articleDates);
    }

    @Test
    void listPageArticlePreviewByDate() {
        Page<Article> articlePage = new Page<>(1, 6);
        Page<Article> pageInfo = articleMapper.listPageArticlePreviewByDate(articlePage);
        for (Article article : pageInfo.getRecords()) {
            System.out.println(article.getCreateTime());
        }
    }
}
