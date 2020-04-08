package site.alanliang.geekblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.domain.ArticleTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/8 9:42
 * Version 1.0
 **/
@SpringBootTest
public class ArticleTagMapperTests {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Test
    @Transactional
    void testBatchInsert() {
        long articleId = 1L;
        List<Long> tagIdList = new ArrayList<>();
        for (long i = 1; i <= 4; i++) {
            tagIdList.add(i);
        }
        articleTagMapper.batchInsert(articleId, tagIdList);
    }

    @Test
    void testSelectList() {
        List<ArticleTag> articleTags = articleTagMapper.selectList(null);
        System.out.println(articleTags);
    }
}
