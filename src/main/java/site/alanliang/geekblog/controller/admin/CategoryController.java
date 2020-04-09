package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.domain.Category;
import site.alanliang.geekblog.service.CategoryService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/7 19:41
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public JsonResult list() {
        return JsonResult.ok(categoryService.list());
    }

    @GetMapping("/list")
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  @RequestParam(value = "keyword", required = false) String keyword) {
        QueryWrapper<Category> wrapper = null;
        if (!StringUtils.isEmpty(keyword)) {
            wrapper = new QueryWrapper<>();
            wrapper.like("name", keyword);
        }
        Page<Category> categoryPage = categoryService.listByPage(page, limit, wrapper);
        return TableResult.tableOk(categoryPage.getRecords(), categoryPage.getTotal());
    }

    @PostMapping
    public JsonResult save(@Validated @RequestBody Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(category.getCreateTime());
        categoryService.saveOfUpdate(category);
        return JsonResult.ok();
    }

    @PutMapping
    public JsonResult update(@Validated @RequestBody Category category) {
        category.setUpdateTime(new Date());
        categoryService.saveOfUpdate(category);
        return JsonResult.ok();
    }

    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        categoryService.remove(id);
        return JsonResult.ok();
    }

    @DeleteMapping
    public JsonResult remove(@NotEmpty @RequestBody List<Long> idList) {
        categoryService.batchRemove(idList);
        return JsonResult.ok();
    }
}
