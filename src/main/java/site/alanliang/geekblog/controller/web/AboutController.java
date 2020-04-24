package site.alanliang.geekblog.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.service.CategoryService;
import site.alanliang.geekblog.service.TagService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 20:13
 * Version 1.0
 **/
@RestController
public class AboutController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/article-count")
    public ResponseEntity<Object> articleCount() {
        long count = articleService.countAll(null);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/category-count")
    public ResponseEntity<Object> categoryCount() {
        long count = categoryService.countAll();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/tag-count")
    public ResponseEntity<Object> tagCount() {
        long count = tagService.countAll();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
