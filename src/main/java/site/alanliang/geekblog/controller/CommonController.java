package site.alanliang.geekblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.service.CategoryService;
import site.alanliang.geekblog.service.TagService;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/3 9:46
 * Version 1.0
 **/
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @GetMapping("/categories/colors")
    public JsonResult categoriesColors() {
        return JsonResult.ok(categoryService.listColor());
    }

    @GetMapping("/tags/colors")
    public JsonResult tagsColors() {
        return JsonResult.ok(tagService.listColor());
    }
}
