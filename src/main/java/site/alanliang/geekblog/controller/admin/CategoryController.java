package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Category;
import site.alanliang.geekblog.query.CategoryQuery;
import site.alanliang.geekblog.service.CategoryService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
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
    public JsonResult listAll() {
        return JsonResult.ok(categoryService.listAll());
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

    @PreAuthorize("hasAuthority('blog:category:query')")
    @OperationLog("查询分类")
    @GetMapping("/list")
    public TableResult listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  CategoryQuery categoryQuery) {
        Page<Category> categoryPage = categoryService.listByPage(page, limit, categoryQuery);
        return TableResult.tableOk(categoryPage.getRecords(), categoryPage.getTotal());
    }

    @PreAuthorize("hasAuthority('blog:category:add')")
    @OperationLog("新增分类")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(category.getCreateTime());
        categoryService.saveOfUpdate(category);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('blog:category:edit')")
    @OperationLog("更新分类")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Category category) {
        category.setUpdateTime(new Date());
        categoryService.saveOfUpdate(category);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('blog:category:delete')")
    @OperationLog("删除分类")
    @DeleteMapping("/{id}")
    public JsonResult batchRemove(@NotNull @PathVariable("id") Long id) {
        categoryService.removeById(id);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('blog:category:delete')")
    @OperationLog("批量删除分类")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        categoryService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @GetMapping("/colors")
    public JsonResult getColors() {
        return JsonResult.ok(categoryService.listColor());
    }
}
