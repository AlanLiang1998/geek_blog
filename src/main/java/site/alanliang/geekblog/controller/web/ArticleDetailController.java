package site.alanliang.geekblog.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.AccessLog;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.dto.ArticleDetailVo;
import site.alanliang.geekblog.service.ArticleService;

import javax.validation.constraints.NotNull;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/18 10:52
 * Version 1.0
 **/
@Controller
public class ArticleDetailController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/article/{id}")
    public String articleDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("article", articleService.getArticleById(id));
        model.addAttribute("prevPreview", articleService.getPrevArticlePreview(id));
        model.addAttribute("nextPreview", articleService.getNextArticlePreview(id));
        return "web/article";
    }

    @ResponseBody
    @PutMapping("/article/{id}/likes")
    public ResponseEntity<Object> likes(@NotNull @PathVariable("id") Long id) {
        articleService.increaseLikes(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AccessLog("阅读文章")
    @ResponseBody
    @GetMapping("/article")
    public ResponseEntity<Object> article(@RequestParam("id") Long id) {
        Article article = articleService.getArticleById(id);
        Article prevPreview = articleService.getPrevArticlePreview(id);
        Article nextPreview = articleService.getNextArticlePreview(id);
        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        articleDetailVo.setArticle(article);
        articleDetailVo.setPrevPreview(prevPreview);
        articleDetailVo.setNextPreview(nextPreview);
        return new ResponseEntity<>(articleDetailVo, HttpStatus.OK);
    }

}
