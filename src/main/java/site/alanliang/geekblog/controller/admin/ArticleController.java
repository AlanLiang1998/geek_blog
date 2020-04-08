package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.dto.ArticleDto;
import site.alanliang.geekblog.service.ArticleService;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/7 23:02
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public JsonResult save(@Validated @RequestBody ArticleDto articleDto) {
        articleDto.setViews(0);
        articleDto.setLikes(0);
        articleDto.setComments(0);
        articleDto.setAppreciable(articleDto.getAppreciable() != null);
        articleDto.setCommentable(articleDto.getCommentable() != null);
        articleDto.setCreateTime(new Date());
        articleDto.setUpdateTime(articleDto.getCreateTime());
        articleService.saveOrUpdate(articleDto);
        return JsonResult.ok();
    }
}
