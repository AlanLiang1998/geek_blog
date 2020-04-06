package site.alanliang.geekblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 21:55
 * Version 1.0
 **/
@Controller
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/page")
    public ModelAndView getPage(@RequestParam("pageName") String pageName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(pageName);
        return modelAndView;
    }
}
