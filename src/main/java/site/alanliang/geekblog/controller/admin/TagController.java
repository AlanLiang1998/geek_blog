package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.domain.Tag;
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
@RestController
@RequestMapping("/admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public JsonResult list() {
        return JsonResult.ok(tagService.list());
    }

    @GetMapping("/list")
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  @RequestParam(value = "keyword", required = false) String keyword) {
        QueryWrapper<Tag> wrapper = null;
        if (!StringUtils.isEmpty(keyword)) {
            wrapper = new QueryWrapper<>();
            wrapper.like("name", keyword);
        }
        Page<Tag> tagPage = tagService.listByPage(page, limit, wrapper);
        return TableResult.tableOk(tagPage.getRecords(), tagPage.getTotal());
    }

    @PostMapping
    public JsonResult save(@Validated @RequestBody Tag tag) {
        tag.setCreateTime(new Date());
        tag.setUpdateTime(tag.getCreateTime());
        tagService.saveOfUpdate(tag);
        return JsonResult.ok();
    }

    @PutMapping
    public JsonResult update(@Validated @RequestBody Tag tag) {
        tag.setUpdateTime(new Date());
        tagService.saveOfUpdate(tag);
        return JsonResult.ok();
    }

    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        tagService.remove(id);
        return JsonResult.ok();
    }

    @DeleteMapping
    public JsonResult remove(@NotEmpty @RequestBody List<Long> idList) {
        tagService.batchRemove(idList);
        return JsonResult.ok();
    }
}
