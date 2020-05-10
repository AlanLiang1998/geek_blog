package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.service.RoleService;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 10:39
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public JsonResult listAll() {
        return JsonResult.ok(roleService.listAll());
    }

}
