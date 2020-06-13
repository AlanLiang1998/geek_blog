package site.alanliang.geekblog.controller.front;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "前台：页面路由")
@Controller
public class FrontRouteController {
    @ApiOperation("前台首页")
    @GetMapping
    public String home() {
        return "front/index";
    }

    @ApiOperation("路由页面")
    @GetMapping("/page/{pageName}")
    public ModelAndView getPage(@PathVariable("pageName") String pageName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front/" + pageName);
        return modelAndView;
    }
}
