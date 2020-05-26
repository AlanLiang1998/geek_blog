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
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Link;
import site.alanliang.geekblog.model.Photo;
import site.alanliang.geekblog.query.LinkQuery;
import site.alanliang.geekblog.query.PhotoQuery;
import site.alanliang.geekblog.service.LinkService;
import site.alanliang.geekblog.service.PhotoService;
import site.alanliang.geekblog.vo.AuditVO;

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
@Api(tags = "相册管理")
@RestController
@RequestMapping("/admin/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

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

    @ApiOperation("分页查询所有照片")
    @PreAuthorize("hasAuthority('blog:photo:query')")
    @OperationLog("查询相册")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       PhotoQuery photoQuery) {
        Page<Photo> pageInfo = photoService.listTableByPage(page, limit, photoQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("删除照片")
    @PreAuthorize("hasAuthority('blog:photo:delete')")
    @OperationLog("删除照片")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        photoService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除照片")
    @PreAuthorize("hasAuthority('blog:photo:delete')")
    @OperationLog("批量删除照片")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        photoService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("添加照片")
    @PreAuthorize("hasAuthority('blog:photo:add')")
    @OperationLog("添加照片")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Photo photo) {
        photo.setCreateTime(new Date());
        photo.setUpdateTime(photo.getCreateTime());
        photoService.saveOfUpdate(photo);
        return JsonResult.ok();
    }

    @ApiOperation("修改照片")
    @PreAuthorize("hasAuthority('blog:photo:edit')")
    @OperationLog("修改照片")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Photo photo) {
        photo.setUpdateTime(new Date());
        photoService.saveOfUpdate(photo);
        return JsonResult.ok();
    }

}
