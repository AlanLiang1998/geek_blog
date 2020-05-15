package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.AccessLog;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.service.MenuService;
import site.alanliang.geekblog.service.UserService;
import site.alanliang.geekblog.utils.MD5Util;
import site.alanliang.geekblog.vo.InitInfoVO;

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
    private MenuService menuService;

    @ResponseBody
    @GetMapping("/init")
    public ResponseEntity<Object> init(HttpSession session) {
        Object o = session.getAttribute("user");
        if (o != null) {
            User user = (User) o;
            InitInfoVO initInfoVO = menuService.menu(user.getId());
            return new ResponseEntity<>(initInfoVO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("当前用户未登录，菜单初始化失败", HttpStatus.BAD_REQUEST);
        }
    }

    @OperationLog("访问后台首页")
    @GetMapping("/index.html")
    public String index() {
        return "admin/home/index";
    }
}
