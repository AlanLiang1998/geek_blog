package site.alanliang.geekblog.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/api/{pageName}")
    public ModelAndView getPage(@PathVariable("pageName") String pageName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("web/" + pageName);
        return modelAndView;
    }
}
