package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.domain.SysAccessLog;
import site.alanliang.geekblog.service.SysAccessLogService;

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
@RequestMapping("/admin/log")
public class SysAccessLogController {

    @Autowired
    private SysAccessLogService sysAccessLogService;

    @GetMapping
    public TableResult list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<SysAccessLog> pageInfo = sysAccessLogService.listByPage(page, limit);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        sysAccessLogService.remove(id);
        return JsonResult.ok();
    }

    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        sysAccessLogService.removeByIds(idList);
        return JsonResult.ok();
    }
}
