package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    /**
     * 将日期格式的String类型转为Date类型
     *
     * @param binder 数据绑定
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        String pattern = "yyyy-MM-dd";
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat(pattern), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @PreAuthorize("hasAuthority('sys:user:query')")
    @OperationLog("查询用户")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  UserQuery userQuery) {
        Page<User> pageInfo = userService.listTableByPage(page, limit, userQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @PreAuthorize("hasAuthority('sys:user:add')")
    @OperationLog("保存用户")
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

    @PreAuthorize("hasAuthority('sys:user:edit')")
    @OperationLog("修改用户")
    @PutMapping
    public JsonResult update(@Validated @RequestBody User user) {
        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        user.setUpdateTime(new Date());
        userService.saveOfUpdate(user);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:user:edit')")
    @OperationLog("修改用户状态")
    @PutMapping("/status")
    public JsonResult changeStatus(@NotNull @RequestParam("userId") Long userId) {
        userService.changeStatus(userId);
        return JsonResult.ok();
    }

    @OperationLog("修改密码")
    @PutMapping("/password")
    public JsonResult changePassword(@Validated @RequestBody UserLoginVO passwordVO) {
        userService.changePassword(passwordVO);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @OperationLog("删除用户")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        userService.removeById(id);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @OperationLog("批量删除用户")
    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList) {
        userService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @GetMapping("/{id}/info")
    public JsonResult getInfo(@PathVariable("id") Long id) {
        return JsonResult.ok(userService.getById(id));
    }

    @OperationLog("修改信息")
    @PutMapping("/info")
    public JsonResult updateInfo(@Validated @RequestBody UserInfoVO userInfoVO) {
        userService.updateInfo(userInfoVO);
        return JsonResult.ok();
    }
}
