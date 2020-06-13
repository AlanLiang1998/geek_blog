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
import site.alanliang.geekblog.model.Tag;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.service.TagService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/20 16:55
 * Version 1.0
 **/
@Api(tags = "前台：标签页面")
@RestController
public class TagsController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @ApiOperation("查询标签")
    @AccessLog("访问标签页")
    @GetMapping("/tags")
    public ResponseEntity<Object> tags() {
        List<Tag> tags = tagService.listByArticleCount();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @ApiOperation("查询标签文章")
    @GetMapping("/tag/{id}/articles")
    public ResponseEntity<Object> tagArticles(@PathVariable("id") Long id,
                                              @RequestParam(value = "current", defaultValue = "1") Integer current,
                                              @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Article> pageInfo = articleService.listPreviewPageByTagId(current, size, id);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }
}
