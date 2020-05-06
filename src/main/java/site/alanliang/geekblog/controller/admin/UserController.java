package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.entity.SysUser;
import site.alanliang.geekblog.service.UserService;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 20:45
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @OperationLog("查询用户")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<SysUser> pageInfo = userService.listByPage(page, limit);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }
}
