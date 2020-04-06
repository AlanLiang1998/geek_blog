package site.alanliang.geekblog.controller.admin;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.alanliang.geekblog.service.SysMenuService;
import site.alanliang.geekblog.vo.init.InitInfo;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 11:46
 * Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private SysMenuService sysMenuService;

    @ResponseBody
    @GetMapping("/init")
    public String init() {
        InitInfo initInfo = sysMenuService.menu();
        return JSON.toJSONString(initInfo);
    }
}
