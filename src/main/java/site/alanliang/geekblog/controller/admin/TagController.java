package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.service.TagService;

/**
 * @Descriptin TODO
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
}
