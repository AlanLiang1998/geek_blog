package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.OperationLog;
import site.alanliang.geekblog.query.LogQuery;
import site.alanliang.geekblog.service.OperationLogService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 17:22
 * Version 1.0
 **/
@Api(tags = "后台：操作日志管理")
@RestController
@RequestMapping("/admin/operation-log")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation("查询操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:query')")
    @site.alanliang.geekblog.anntation.OperationLog("查询操作日志")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  LogQuery logQuery) {
        Page<OperationLog> pageInfo = operationLogService.listByPage(page, limit, logQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("删除操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @site.alanliang.geekblog.anntation.OperationLog("删除操作日志")
    @DeleteMapping("/{id}")
    public JsonResult removeById(@NotNull @PathVariable("id") Long id) {
        operationLogService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @site.alanliang.geekblog.anntation.OperationLog("批量删除操作日志")
    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList) {
        operationLogService.removeByIdList(idList);
        return JsonResult.ok();
    }
}
