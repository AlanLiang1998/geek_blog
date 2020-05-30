package site.alanliang.geekblog.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.anntation.AccessLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.vo.HomeVO;

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

    @AccessLog("访问首页")
    @GetMapping("/home")
    public ResponseEntity<Object> home() {
        HomeVO homeVo = new HomeVO();
        homeVo.setTopArticles(articleService.listTop());
        homeVo.setRecommendArticles(articleService.listRecommend());
        homeVo.setPageInfo(articleService.listPreviewByPage(1, Integer.parseInt(Constant.PAGE_SIZE)));
        return new ResponseEntity<>(homeVo, HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<Object> articles(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Article> articlePage = articleService.listPreviewByPage(current, size);
        return new ResponseEntity<>(articlePage, HttpStatus.OK);
    }

    @AccessLog("搜索文章")
    @GetMapping(value = "/articles/search")
    public List<Article> search(@RequestParam(value = "keyword", required = false) String keyword) {
        return articleService.listByKeyword();
    }
}
