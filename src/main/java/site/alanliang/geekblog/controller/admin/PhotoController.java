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
import site.alanliang.geekblog.model.Photo;
import site.alanliang.geekblog.query.PhotoQuery;
import site.alanliang.geekblog.service.PhotoService;

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
@Api(tags = "后台：相册管理")
@RestController
@RequestMapping("/admin/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @ApiOperation("查询照片")
    @PreAuthorize("hasAuthority('blog:photo:query')")
    @OperationLog("查询照片")
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

    @ApiOperation("新增照片")
    @PreAuthorize("hasAuthority('blog:photo:add')")
    @OperationLog("新增照片")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Photo photo) {
        photo.setCreateTime(new Date());
        photo.setUpdateTime(photo.getCreateTime());
        photoService.saveOfUpdate(photo);
        return JsonResult.ok();
    }

    @ApiOperation("更新照片")
    @PreAuthorize("hasAuthority('blog:photo:edit')")
    @OperationLog("更新照片")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Photo photo) {
        photo.setUpdateTime(new Date());
        photoService.saveOfUpdate(photo);
        return JsonResult.ok();
    }

}
