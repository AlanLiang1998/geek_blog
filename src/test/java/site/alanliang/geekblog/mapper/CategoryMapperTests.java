package site.alanliang.geekblog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.domain.Category;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/16 18:32
 * Version 1.0
 **/
@SpringBootTest
public class CategoryMapperTests {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void listByArticleCount(){
        List<Category> categories = categoryMapper.listByArticleCount();
        System.out.println(categories);
    }
}
