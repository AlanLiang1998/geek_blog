package site.alanliang.geekblog.controller.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.service.LinkService;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 17:05
 * Version 1.0
 **/
@Api(tags = "前台友链页面")
@RestController
@RequestMapping("/links")
public class LinksController {

    @Autowired
    private LinkService linkService;

    @ApiOperation("分页查询友链")
    @GetMapping
    public ResponseEntity<Object> listByPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return new ResponseEntity<>(linkService.listByPage(current, size), HttpStatus.OK);
    }

}
