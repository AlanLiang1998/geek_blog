package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.vo.ArticleVO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

    /**
     * 将日期格式的String类型转为Date类型
     *
     * @param binder 数据绑定
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        String pattern = "yyyy-MM-dd";
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat(pattern), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @OperationLog("查询文章")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       ArticleQuery articleQuery) {
        Page<Article> pageInfo = articleService.listTableByPage(page, limit, articleQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @OperationLog("新增文章")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Article article) {
        article.setViews(0);
        article.setLikes(0);
        article.setComments(0);
        article.setAppreciable(article.getAppreciable() != null);
        article.setCommentable(article.getCommentable() != null);
        article.setTop(article.getTop() != null);
        article.setRecommend(article.getRecommend() != null);
        article.setCreateTime(new Date());
        article.setUpdateTime(article.getCreateTime());
        articleService.saveOrUpdate(article);
        return JsonResult.ok();
    }

    @OperationLog("更新文章")
    @PutMapping
    public JsonResult update(@Validated @RequestBody ArticleVO articleVo) {
        articleVo.setAppreciable(articleVo.getAppreciable() != null);
        articleVo.setCommentable(articleVo.getCommentable() != null);
        articleVo.setTop(articleVo.getTop() != null);
        articleVo.setRecommend(articleVo.getRecommend() != null);
        articleVo.setUpdateTime(new Date());
        articleService.saveOrUpdate(articleVo);
        return JsonResult.ok();
    }

    @OperationLog("删除文章")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        articleService.removeById(id);
        return JsonResult.ok();
    }

    @OperationLog("批量删除文章")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        articleService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @GetMapping("/reachedMaxTop")
    public JsonResult reachedMaxTop() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("reached", articleService.reachedMaxTop());
        map.put("maxTop", Constant.MAX_TOP_ARTICLES);
        return JsonResult.ok(map);
    }

    @GetMapping("/reachedMaxRecommend")
    public JsonResult reachedMaxRecommend() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("reached", articleService.reachedMaxRecommend());
        map.put("maxRecommend", Constant.MAX_RECOMMEND_ARTICLES);
        return JsonResult.ok(map);
    }
}
