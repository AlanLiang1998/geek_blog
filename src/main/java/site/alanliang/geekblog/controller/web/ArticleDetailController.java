package site.alanliang.geekblog.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.service.ArticleService;

import javax.validation.constraints.NotNull;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/18 10:52
 * Version 1.0
 **/
@Controller
@RequestMapping("/article")
public class ArticleDetailController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id}")
    public String articleDetail(@PathVariable("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "web/article";
    }

    @ResponseBody
    @PostMapping("/{id}/likes")
    public ResponseEntity<Object> likes(@NotNull @PathVariable("id") Long id) {
        articleService.increaseLikes(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
