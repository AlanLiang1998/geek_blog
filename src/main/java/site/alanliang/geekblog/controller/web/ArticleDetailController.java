package site.alanliang.geekblog.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/18 10:52
 * Version 1.0
 **/
@Controller
public class ArticleDetailController {
    @GetMapping("/article")
    public String articleDetail() {
        return "web/article";
    }
}
