package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.domain.Tag;

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
    void listByRecommend() {
        List<Article> articles = articleMapper.listByRecommend(4);
        System.out.println(articles);
    }

    @Test
    void listByPageForWeb(){
        Page<Article> articlePage = new Page<>(1, 6);
        List<Article> articles = articleMapper.selectPageForWeb(articlePage, null);
        List<Tag> tagList = articles.get(0).getTagList();
        System.out.println(tagList);
    }

}
