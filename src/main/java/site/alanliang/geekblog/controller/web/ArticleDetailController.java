package site.alanliang.geekblog.controller.web;

import com.sun.media.sound.SoftTuning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.service.ArticleService;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

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
        model.addAttribute("id", id);
        return "web/article";
    }

    @ResponseBody
    @PutMapping("/article/{id}/likes")
    public ResponseEntity<Object> likes(@NotNull @PathVariable("id") Long id) {
        articleService.increaseLikes(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/article")
    public ResponseEntity<Object> article(@RequestParam("id") Long id) {
        Article article = articleService.getArticleById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/article/prev-and-next")
    ResponseEntity<Object> prevAndNextArticlePreview(@RequestParam("id") Long id) {
        Article prevPreview = articleService.getPrevArticlePreview(id);
        Article nextPreview = articleService.getNextArticlePreview(id);
        Map<String, Object> map = new HashMap<>();
        map.put("prevPreview", prevPreview);
        map.put("nextPreview", nextPreview);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
