package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.OperationLog;
import site.alanliang.geekblog.query.LogQuery;
import site.alanliang.geekblog.service.OperationLogService;

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
@RequestMapping("/admin/operation-log")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

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

    @PreAuthorize("hasAuthority('sys:operationlog:query')")
    @site.alanliang.geekblog.anntation.OperationLog("查询操作日志")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  LogQuery logQuery) {
        Page<OperationLog> pageInfo = operationLogService.listByPage(page, limit, logQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @site.alanliang.geekblog.anntation.OperationLog("删除操作日志")
    @DeleteMapping("/{id}")
    public JsonResult removeById(@NotNull @PathVariable("id") Long id) {
        operationLogService.remove(id);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @site.alanliang.geekblog.anntation.OperationLog("批量删除操作日志")
    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList) {
        operationLogService.removeByIdList(idList);
        return JsonResult.ok();
    }
}
