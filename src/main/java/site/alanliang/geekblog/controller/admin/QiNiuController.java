package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.QiniuConfig;
import site.alanliang.geekblog.model.QiniuContent;
import site.alanliang.geekblog.query.QiNiuQuery;
import site.alanliang.geekblog.service.QiNiuService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/qiNiuContent")
@Api(tags = "七牛云存储管理")
public class QiNiuController {
    @Autowired
    private QiNiuService qiNiuService;

    @GetMapping(value = "/config")
    public ResponseEntity<Object> get() {
        return new ResponseEntity<>(qiNiuService.find(), HttpStatus.OK);
    }

    @OperationLog("查询文件")
    @ApiOperation("查询文件")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       QiNiuQuery qiNiuQuery) {
        Page<QiniuContent> pageInfo = qiNiuService.queryAll(page, limit, qiNiuQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @OperationLog("配置七牛云存储")
    @ApiOperation("配置七牛云存储")
    @PutMapping(value = "/config")
    public ResponseEntity<Object> config(@Validated @RequestBody QiniuConfig qiniuConfig) {
        qiNiuService.update(qiniuConfig);
        qiNiuService.update(qiniuConfig.getType());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @OperationLog("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, QiNiuQuery qiNiuQuery) throws IOException {
        qiNiuService.downloadList(qiNiuService.queryAll(qiNiuQuery), response);
    }

    @OperationLog("上传文件")
    @ApiOperation("上传文件")
    @PostMapping
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) {
        qiNiuService.upload(files, qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @OperationLog("同步七牛云数据")
    @ApiOperation("同步七牛云数据")
    @PostMapping(value = "/synchronize")
    public ResponseEntity<Object> synchronize() {
        qiNiuService.synchronize(qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @OperationLog("下载文件")
    @ApiOperation("下载文件")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Object> download(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("url", qiNiuService.download(qiNiuService.findByContentId(id), qiNiuService.find()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @OperationLog("删除文件")
    @ApiOperation("删除文件")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        qiNiuService.delete(id, qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @OperationLog("删除多张图片")
    @ApiOperation("删除多张图片")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody List<Long> idList) {
        qiNiuService.deleteAll(idList, qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
