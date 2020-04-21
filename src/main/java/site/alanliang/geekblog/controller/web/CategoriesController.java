package site.alanliang.geekblog.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.domain.Category;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.service.CategoryService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/20 16:55
 * Version 1.0
 **/
@RestController
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/categories")
    public ResponseEntity<Object> categories() {
        List<Category> categories = categoryService.listByArticleCount();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}/articles")
    public ResponseEntity<Object> categoryArticles(@PathVariable("id") Long id,
                                                   @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                   @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Article> pageInfo = articleService.listPageArticlePreviewByCategoryId(current, size, id);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }
}
