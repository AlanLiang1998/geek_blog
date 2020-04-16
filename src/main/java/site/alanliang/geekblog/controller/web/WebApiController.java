package site.alanliang.geekblog.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/16 18:02
 * Version 1.0
 **/
@Controller
public class WebApiController {
    @GetMapping
    public String home() {
        return "web/index";
    }
}
