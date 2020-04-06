package site.alanliang.geekblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 21:55
 * Version 1.0
 **/
@Controller
@RequestMapping("/api")
public class ApiController {

    @RequestMapping
    public String index() {
        return "index";
    }

}
