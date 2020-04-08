package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.service.CategoryService;

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
}
