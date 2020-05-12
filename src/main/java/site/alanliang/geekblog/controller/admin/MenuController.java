package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.service.MenuService;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 8:53
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @OperationLog("查询菜单")
    @GetMapping
    public TableResult listAll() {
        return TableResult.tableOk(menuService.listAll(), menuService.countAll());
    }

    @GetMapping("/tree")
    public JsonResult listByTree() {
        return JsonResult.ok(menuService.listByTree());
    }

    @OperationLog("保存菜单")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Menu menu) {
        menu.setStatus(menu.getStatus() != null);
        menu.setCreateTime(new Date());
        menu.setUpdateTime(menu.getCreateTime());
        menuService.saveOfUpdate(menu);
        return JsonResult.ok();
    }

    @OperationLog("修改菜单")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Menu menu) {
        menu.setStatus(menu.getStatus() != null);
        menu.setUpdateTime(new Date());
        menuService.saveOfUpdate(menu);
        return JsonResult.ok();
    }

    @OperationLog("删除菜单")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        menuService.removeById(id);
        return JsonResult.ok();
    }
}
