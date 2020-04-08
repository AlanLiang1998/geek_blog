package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.dto.ArticleDto;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.vo.ArticleVo;

import java.util.Date;
import java.util.List;

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

    @GetMapping
    public TableResult list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<ArticleVo> articleVos = articleService.listByPage(page, limit);
        long count = articleService.countAll();
        return TableResult.tableOk(articleVos, count);
    }

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

    public JsonResult update(@Validated @RequestBody ArticleDto articleDto) {
        articleDto.setAppreciable(articleDto.getAppreciable() != null);
        articleDto.setCommentable(articleDto.getCommentable() != null);
        articleDto.setUpdateTime(new Date());
        articleService.saveOrUpdate(articleDto);
        return JsonResult.ok();
    }
}
