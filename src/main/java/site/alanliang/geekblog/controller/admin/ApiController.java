package site.alanliang.geekblog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.alanliang.geekblog.mapper.SysMenuMapper;
import site.alanliang.geekblog.service.SysMenuService;
import site.alanliang.geekblog.vo.init.InitInfo;

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
