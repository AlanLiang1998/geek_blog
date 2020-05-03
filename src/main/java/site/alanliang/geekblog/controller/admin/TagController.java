package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.entity.Tag;
import site.alanliang.geekblog.query.TagQuery;
import site.alanliang.geekblog.service.TagService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
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
    public JsonResult listAll() {
        return JsonResult.ok(tagService.list());
    }

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

    @GetMapping("/list")
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  TagQuery tagQuery) {
        Page<Tag> tagPage = tagService.listByPage(page, limit, tagQuery);
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
        tagService.removeById(id);
        return JsonResult.ok();
    }

    @DeleteMapping
    public JsonResult remove(@NotEmpty @RequestBody List<Long> idList) {
        tagService.removeByIds(idList);
        return JsonResult.ok();
    }
}
