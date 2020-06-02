package site.alanliang.geekblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.vo.ArticleDateVO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void countByDate() {
        List<ArticleDateVO> articleDateVOS = articleMapper.countByDate(2);
        System.out.println(articleDateVOS);
    }
}