package site.alanliang.geekblog.controller.admin;

import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/14 17:41
 * Version 1.0
 **/
@Api(tags = "后台：安全控制")
@Controller
@RequestMapping("/admin")
public class SecurityController {

    @ApiOperation("登录页面")
    @GetMapping({"/", "/login.html"})
    public String loginPage() {
        return "admin/home/login";
    }

    @ApiOperation("403页面")
    @GetMapping(value = "/403.html")
    public String noPermission() {
        return "error/403";
    }

    @ApiOperation("验证码")
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }
}
