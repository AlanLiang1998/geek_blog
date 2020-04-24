package site.alanliang.geekblog.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<Object> listTopArticles() {
        List<Article> articles = articleService.listTopArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/recommend-articles")
    public ResponseEntity<Object> listRecommendArticles() {
        List<Article> articles = articleService.listRecommendArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/articles/{current}")
    public ResponseEntity<Object> listArticlesByPage(@PathVariable(value = "current") Integer current,
                                                     @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        if (current == null) {
            current = 1;
        }
        Page<Article> articlePage = articleService.listArticlesByPage(current, size);
        return new ResponseEntity<>(articlePage, HttpStatus.OK);
    }

    @GetMapping(value = "/articles/search")
    public List<Article> search(@RequestParam(value = "keyword", required = false) String keyword) {
        return articleService.listArticlesByKeyword();
    }
}
