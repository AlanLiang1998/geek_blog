package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.LocalStorage;
import site.alanliang.geekblog.query.LocalStorageQuery;
import site.alanliang.geekblog.service.LocalStorageService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 10:16
 * Version 1.0
 **/
@Api(tags = "本地存储管理")
@RestController
@RequestMapping("/admin/localStorage")
public class LocalStorageController {

    @Autowired
    private LocalStorageService localStorageService;

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

    @ApiOperation("分页查询所有本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:query')")
    @OperationLog("查询本地文件")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       LocalStorageQuery localStorageQuery) {
        Page<LocalStorage> pageInfo = localStorageService.listTableByPage(page, limit, localStorageQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("删除本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:delete')")
    @OperationLog("删除本地文件")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        localStorageService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:delete')")
    @OperationLog("批量删除本地文件")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        localStorageService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("上传本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:add')")
    @OperationLog("上传本地文件")
    @PostMapping
    public JsonResult upload(@RequestParam("file") MultipartFile[] multipartFiles) {
        localStorageService.upload(multipartFiles);
        return JsonResult.ok();
    }

    @ApiOperation("修改本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:edit')")
    @OperationLog("修改本地文件")
    @PutMapping
    public JsonResult update(@Validated @RequestBody LocalStorage localStorage) {
        localStorage.setUpdateTime(new Date());
        localStorageService.update(localStorage);
        return JsonResult.ok();
    }

}
