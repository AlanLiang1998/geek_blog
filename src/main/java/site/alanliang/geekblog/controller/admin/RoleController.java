package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Role;
import site.alanliang.geekblog.query.RoleQuery;
import site.alanliang.geekblog.service.RoleService;
import site.alanliang.geekblog.utils.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

    @PreAuthorize("hasAuthority('sys:role:query')")
    @GetMapping("/list")
    public JsonResult listAll() {
        return JsonResult.ok(roleService.listAll());
    }

    @PreAuthorize("hasAuthority('sys:role:query')")
    @OperationLog("查询角色")
    @GetMapping
    public JsonResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                 RoleQuery roleQuery) {
        Page<Role> pageInfo = roleService.listTableByPage(page, limit, roleQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @PreAuthorize("hasAuthority('sys:role:add')")
    @OperationLog("保存角色")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Role role) {
        if (role.getStatus() == null) {
            role.setStatus(0);
        }
        if (StringUtils.isEmpty(role.getDescription())) {
            role.setDescription("-");
        }
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        roleService.saveOfUpdate(role);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:role:edit')")
    @OperationLog("修改角色")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Role role) {
        if (role.getStatus() == null) {
            role.setStatus(0);
        }
        if (StringUtils.isEmpty(role.getDescription())) {
            role.setDescription("-");
        }
        role.setUpdateTime(new Date());
        roleService.saveOfUpdate(role);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:role:delete')")
    @OperationLog("删除角色")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        roleService.removeById(id);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:role:delete')")
    @OperationLog("批量删除角色")
    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList) {
        roleService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:role:edit')")
    @OperationLog("修改角色状态")
    @PutMapping("/status")
    public JsonResult changeStatus(@RequestBody Role role) {
        roleService.changeStatus(role);
        return JsonResult.ok();
    }
}
