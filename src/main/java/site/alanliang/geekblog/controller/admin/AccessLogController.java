package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.AccessLog;
import site.alanliang.geekblog.query.LogQuery;
import site.alanliang.geekblog.service.AccessLogService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 17:22
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/access-log")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

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

    @PreAuthorize("hasAuthority('sys:accesslog:query')")
    @OperationLog("查询访问日志")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  LogQuery logQuery) {
        Page<AccessLog> pageInfo = accessLogService.listByPage(page, limit, logQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @PreAuthorize("hasAuthority('sys:accesslog:delete')")
    @OperationLog("删除访问日志")
    @DeleteMapping("/{id}")
    public JsonResult removeById(@NotNull @PathVariable("id") Long id) {
        accessLogService.remove(id);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:accesslog:delete')")
    @OperationLog("批量删除访问日志")
    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList) {
        accessLogService.removeByIdList(idList);
        return JsonResult.ok();
    }
}
