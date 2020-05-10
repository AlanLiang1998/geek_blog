package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.service.CategoryService;
import site.alanliang.geekblog.service.TagService;
import site.alanliang.geekblog.service.UserService;
import site.alanliang.geekblog.service.impl.UserServiceImpl;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 21:55
 * Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class AdminApiController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/{moduleName}/{pageName}")
    public ModelAndView getPage(@PathVariable("moduleName") String moduleName, @PathVariable("pageName") String pageName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/" + moduleName + "/" + pageName);
        return modelAndView;
    }

    @GetMapping("/article/{id}")
    public String editArticle(@PathVariable("id") Long id, Model model) {
        model.addAttribute("article", articleService.getById(id));
        model.addAttribute("tagList", tagService.listByArticleId(id));
        return "admin/article/article-edit";
    }

    @GetMapping("/category/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "admin/category/category-edit";
    }

    @GetMapping("/tag/{id}")
    public String editTag(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tag", tagService.getById(id));
        return "admin/tag/tag-edit";
    }

    @GetMapping("/user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/user/user-edit";
    }
}
