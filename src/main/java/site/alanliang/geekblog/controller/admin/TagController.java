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
import site.alanliang.geekblog.model.Tag;
import site.alanliang.geekblog.query.TagQuery;
import site.alanliang.geekblog.service.TagService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin 标签控制器
 * @Author AlanLiang
 * Date 2020/4/7 20:34
 * Version 1.0
 **/
@Api(tags = "后台：标签管理")
@RestController
@RequestMapping("/admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("查询所有标签")
    @GetMapping
    public JsonResult listAll() {
        return JsonResult.ok(tagService.listAll());
    }

    @ApiOperation("查询标签")
    @PreAuthorize("hasAuthority('blog:tag:query')")
    @OperationLog("查询标签")
    @GetMapping("/list")
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  TagQuery tagQuery) {
        Page<Tag> tagPage = tagService.listTableByPage(page, limit, tagQuery);
        return TableResult.tableOk(tagPage.getRecords(), tagPage.getTotal());
    }

    @ApiOperation("新增标签")
    @PreAuthorize("hasAuthority('blog:tag:add')")
    @OperationLog("新增标签")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Tag tag) {
        tag.setCreateTime(new Date());
        tag.setUpdateTime(tag.getCreateTime());
        tagService.saveOfUpdate(tag);
        return JsonResult.ok();
    }

    @ApiOperation("更新标签")
    @PreAuthorize("hasAuthority('blog:tag:edit')")
    @OperationLog("更新标签")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Tag tag) {
        tag.setUpdateTime(new Date());
        tagService.saveOfUpdate(tag);
        return JsonResult.ok();
    }

    @ApiOperation("删除标签")
    @PreAuthorize("hasAuthority('blog:tag:delete')")
    @OperationLog("删除标签")
    @DeleteMapping("/{id}")
    public JsonResult batchRemove(@NotNull @PathVariable("id") Long id) {
        tagService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除标签")
    @PreAuthorize("hasAuthority('blog:tag:delete')")
    @OperationLog("批量删除标签")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        tagService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("查询标签颜色")
    @GetMapping("/colors")
    public JsonResult getColors() {
        return JsonResult.ok(tagService.listColor());
    }
}
