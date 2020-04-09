package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.mapper.ArticleMapper;
import site.alanliang.geekblog.vo.ArticleVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/8 16:09
 * Version 1.0
 **/
@SpringBootTest
public class ArticleServiceImplTests {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void testListByPage() {
        Page<Article> page = new Page<>(1, 5);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.like("title", "1");
        List<Article> articles = articleMapper.selectPageWithExtra(page, wrapper);
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVos.add(articleVo);
        }
        System.out.println(articleVos);
    }
}
