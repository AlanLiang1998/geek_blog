package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.Log;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.domain.SysUser;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.service.SysMenuService;
import site.alanliang.geekblog.service.SysUserService;
import site.alanliang.geekblog.utils.MD5Util;
import site.alanliang.geekblog.vo.init.InitInfoVo;

import javax.servlet.http.HttpSession;

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
    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    public String toLogin(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "admin/home/index";
        }
        return "admin/home/login";
    }

    @ResponseBody
    @GetMapping("/init")
    public ResponseEntity<Object> init() {
        InitInfoVo initInfoVO = sysMenuService.menu();
        return new ResponseEntity<>(initInfoVO, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/login")
    public JsonResult toLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session) {
        SysUser user = sysUserService.checkUser(username, MD5Util.code(password));
        if (user != null) {
            session.setAttribute("user", user);
            return JsonResult.ok();
        } else {
            throw new BadRequestException("用户名或密码错误");
        }
    }

    @GetMapping("/index")
    public String index() {
        return "admin/home/index";
    }

    @GetMapping("/401")
    public String unauthorized() {
        return "error/401";
    }
}
