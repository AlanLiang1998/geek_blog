package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.entity.SysOperationLog;
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
@RestController
@RequestMapping("/admin/operation-log")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<SysOperationLog> pageInfo = operationLogService.listByPage(page, limit);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @DeleteMapping("/{id}")
    public JsonResult removeById(@NotNull @PathVariable("id") Long id) {
        operationLogService.remove(id);
        return JsonResult.ok();
    }

    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList) {
        operationLogService.removeByIds(idList);
        return JsonResult.ok();
    }
}
