package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.dto.ArticleVo;
import site.alanliang.geekblog.service.ArticleService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                            @RequestParam(value = "keyword", required = false) String keyword) {
        QueryWrapper<Article> wrapper = null;
        if (!StringUtils.isEmpty(keyword)) {
            wrapper = new QueryWrapper<>();
            wrapper.like("title", keyword);
        }
        List<site.alanliang.geekblog.vo.ArticleVo> articleVos = articleService.listByPageForAdmin(page, limit, wrapper);
        long count = articleService.countAll(wrapper);
        return TableResult.tableOk(articleVos, count);
    }

    @PostMapping
    public JsonResult save(@Validated @RequestBody ArticleVo articleVo) {
        articleVo.setViews(0);
        articleVo.setLikes(0);
        articleVo.setComments(0);
        articleVo.setAppreciable(articleVo.getAppreciable() != null);
        articleVo.setCommentable(articleVo.getCommentable() != null);
        articleVo.setTop(articleVo.getTop() != null);
        articleVo.setRecommend(articleVo.getRecommend() != null);
        articleVo.setCreateTime(new Date());
        articleVo.setUpdateTime(articleVo.getCreateTime());
        articleService.saveOrUpdate(articleVo);
        return JsonResult.ok();
    }

    @PutMapping
    public JsonResult update(@Validated @RequestBody ArticleVo articleVo) {
        articleVo.setAppreciable(articleVo.getAppreciable() != null);
        articleVo.setCommentable(articleVo.getCommentable() != null);
        articleVo.setTop(articleVo.getTop() != null);
        articleVo.setRecommend(articleVo.getRecommend() != null);
        articleVo.setUpdateTime(new Date());
        articleService.saveOrUpdate(articleVo);
        return JsonResult.ok();
    }

    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        articleService.remove(id);
        return JsonResult.ok();
    }

    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        articleService.removeByIds(idList);
        return JsonResult.ok();
    }
}
