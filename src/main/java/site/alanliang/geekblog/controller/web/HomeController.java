package site.alanliang.geekblog.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.service.ArticleService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/14 17:47
 * Version 1.0
 **/
@RestController
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/top-articles")
    public ResponseEntity<Object> getTopArticles() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "summary", "cover")
                .eq("published", true)
                .eq("top", true)
                .last("limit " + Constant.MAX_TOP_ARTICLES);
        List<Article> articles = articleService.listByWrapper(wrapper);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/recommend-articles")
    public ResponseEntity<Object> getRecommendArticles() {
        List<Article> articles = articleService.listByRecommend(Constant.MAX_RECOMMEND_ARTICLES);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/articles/{current}")
    public ResponseEntity<Object> getArticlesByPage(@PathVariable(value = "current") Integer current,
                                                    @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        if (current == null) {
            current = 1;
        }
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("published", true)
                .orderByDesc("create_time");
        List<Article> articles = articleService.listByPageForWeb(current, size, wrapper);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}
