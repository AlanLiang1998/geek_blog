package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Link;
import site.alanliang.geekblog.query.LinkQuery;
import site.alanliang.geekblog.service.LinkService;
import site.alanliang.geekblog.vo.AuditVO;

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
@Api(tags = "后台：友链管理")
@RestController
@RequestMapping("/admin/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @ApiOperation("查询友链")
    @PreAuthorize("hasAuthority('blog:link:query')")
    @OperationLog("查询友链")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       LinkQuery linkQuery) {
        Page<Link> pageInfo = linkService.listTableByPage(page, limit, linkQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("审核友链")
    @PreAuthorize("hasAuthority('blog:link:audit')")
    @OperationLog("审核友链")
    @PutMapping("/audit")
    public JsonResult audit(@Validated @RequestBody AuditVO auditVO) {
        linkService.audit(auditVO);
        return JsonResult.ok();
    }

    @ApiOperation("删除友链")
    @PreAuthorize("hasAuthority('blog:link:delete')")
    @OperationLog("删除友链")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        linkService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除友链")
    @PreAuthorize("hasAuthority('blog:link:delete')")
    @OperationLog("批量删除友链")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        linkService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("新增友链")
    @PreAuthorize("hasAuthority('blog:link:add')")
    @OperationLog("新增友链")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Link link) {
        link.setStatus(Constant.AUDIT_WAIT);
        link.setCreateTime(new Date());
        link.setUpdateTime(link.getCreateTime());
        linkService.saveOfUpdate(link);
        return JsonResult.ok();
    }

    @ApiOperation("更新友链")
    @PreAuthorize("hasAuthority('blog:link:edit')")
    @OperationLog("更新友链")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Link link) {
        link.setStatus(Constant.AUDIT_WAIT);
        link.setUpdateTime(new Date());
        linkService.saveOfUpdate(link);
        return JsonResult.ok();
    }

}
