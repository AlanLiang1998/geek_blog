package site.alanliang.geekblog.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.anntation.AccessLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.model.Category;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.service.CategoryService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/20 16:55
 * Version 1.0
 **/
@Api(tags = "前台：分类页面")
@RestController
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @ApiOperation("查询分类页面数据")
    @AccessLog("访问分类页面")
    @GetMapping("/categories")
    public ResponseEntity<Object> categories() {
        List<Category> categories = categoryService.listByArticleCount();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @ApiOperation("查询分类文章")
    @GetMapping("/category/{id}/articles")
    public ResponseEntity<Object> categoryArticles(@PathVariable("id") Long id,
                                                   @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                   @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Article> pageInfo = articleService.listPreviewPageByCategoryId(current, size, id);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }
}
