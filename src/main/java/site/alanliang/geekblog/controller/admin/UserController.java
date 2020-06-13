package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.query.UserQuery;
import site.alanliang.geekblog.service.UserService;
import site.alanliang.geekblog.vo.UserInfoVO;
import site.alanliang.geekblog.vo.UserLoginVO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 20:45
 * Version 1.0
 **/
@Api(tags = "后台：用户管理")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查询用户")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @OperationLog("查询用户")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  UserQuery userQuery) {
        Page<User> pageInfo = userService.listTableByPage(page, limit, userQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("新增用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @OperationLog("新增用户")
    @PostMapping
    public JsonResult save(@Validated @RequestBody User user) {
        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(Constant.DEFAULT_PASSWORD));
        user.setAvatar(Constant.DEFAULT_AVATAR);
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        userService.saveOfUpdate(user);
        return JsonResult.ok();
    }

    @ApiOperation("更新用户")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @OperationLog("更新用户")
    @PutMapping
    public JsonResult update(@Validated @RequestBody User user) {
        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        user.setUpdateTime(new Date());
        userService.saveOfUpdate(user);
        return JsonResult.ok();
    }

    @ApiOperation("更新用户状态")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @OperationLog("更新用户状态")
    @PutMapping("/status")
    public JsonResult changeStatus(@RequestBody User user) {
        userService.changeStatus(user);
        return JsonResult.ok();
    }

    @ApiOperation("修改密码")
    @OperationLog("修改密码")
    @PutMapping("/password")
    public JsonResult changePassword(@Validated @RequestBody UserLoginVO passwordVO) {
        userService.changePassword(passwordVO);
        return JsonResult.ok();
    }

    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @OperationLog("删除用户")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        userService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除用户")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @OperationLog("批量删除用户")
    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList) {
        userService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("查询个人信息")
    @GetMapping("/{id}/info")
    public JsonResult getInfo(@PathVariable("id") Long id) {
        return JsonResult.ok(userService.getById(id));
    }

    @ApiOperation("更新个人信息")
    @OperationLog("更新个人信息")
    @PutMapping("/info")
    public JsonResult updateInfo(@Validated @RequestBody UserInfoVO userInfoVO) {
        userService.updateInfo(userInfoVO);
        return JsonResult.ok();
    }
}
