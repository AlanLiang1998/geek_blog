package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Notice;
import site.alanliang.geekblog.query.NoticeQuery;
import site.alanliang.geekblog.service.NoticeService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 10:16
 * Version 1.0
 **/
@Api(tags = "公告管理")
@RestController
@RequestMapping("/admin/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation("分页查询所有公告")
    @PreAuthorize("hasAuthority('sys:notice:query')")
    @OperationLog("查询公告")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       NoticeQuery noticeQuery) {
        Page<Notice> pageInfo = noticeService.listTableByPage(page, limit, noticeQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("删除公告")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    @OperationLog("删除公告")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        noticeService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除公告")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    @OperationLog("批量删除公告")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        noticeService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("添加公告")
    @PreAuthorize("hasAuthority('sys:notice:add')")
    @OperationLog("添加公告")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Notice notice) {
        notice.setCreateTime(new Date());
        notice.setUpdateTime(notice.getCreateTime());
        noticeService.saveOfUpdate(notice);
        return JsonResult.ok();
    }

    @ApiOperation("修改公告")
    @PreAuthorize("hasAuthority('sys:notice:edit')")
    @OperationLog("修改公告")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Notice notice) {
        notice.setUpdateTime(new Date());
        noticeService.saveOfUpdate(notice);
        return JsonResult.ok();
    }

}
