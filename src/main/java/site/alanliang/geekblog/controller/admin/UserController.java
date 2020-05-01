package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import site.alanliang.geekblog.anntation.Log;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.entity.SysUser;
import site.alanliang.geekblog.service.UserService;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 20:45
 * Version 1.0
 **/
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Log("分页查询用户")
    @ResponseBody
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<SysUser> pageInfo = userService.listByPage(page, limit);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }
}
