package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.alanliang.geekblog.anntation.Log;
import site.alanliang.geekblog.service.SysMenuService;
import site.alanliang.geekblog.vo.init.InitInfoVo;

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

    @Log("初始化后台主页")
    @ResponseBody
    @GetMapping("/init")
    public ResponseEntity<Object> init() {
        InitInfoVo initInfoVO = sysMenuService.menu();
        return new ResponseEntity<>(initInfoVO, HttpStatus.OK);
    }

}
