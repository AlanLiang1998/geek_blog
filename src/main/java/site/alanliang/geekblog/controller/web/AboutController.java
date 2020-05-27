package site.alanliang.geekblog.controller.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.anntation.AccessLog;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.service.CategoryService;
import site.alanliang.geekblog.service.PhotoService;
import site.alanliang.geekblog.service.TagService;
import site.alanliang.geekblog.utils.DateUtil;
import site.alanliang.geekblog.vo.AboutVO;
import site.alanliang.geekblog.vo.ArticleDateVO;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 20:13
 * Version 1.0
 **/
@Api(tags = "前台关于我页面")
@RestController
public class AboutController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PhotoService photoService;

    @ApiOperation("查询关于我页数据")
    @AccessLog("访问关于我页")
    @GetMapping("/about")
    public ResponseEntity<Object> about(@RequestParam(value = "dateType", required = false) Integer dateFilterType) {
        AboutVO aboutVO = new AboutVO();
        aboutVO.setArticleCount(articleService.countAll());
        aboutVO.setCategoryCount(categoryService.countAll());
        aboutVO.setTagCount(tagService.countAll());
        aboutVO.setCategories(categoryService.listByArticleCount());
        aboutVO.setTags(tagService.listByArticleCount());
        aboutVO.setPhotos(photoService.listAll());
        List<ArticleDateVO> articleDates = articleService.countByDate(dateFilterType);
        for (ArticleDateVO articleDate : articleDates) {
            articleDate.setDate(DateUtil.formatDate(articleDate.getYear(), articleDate.getMonth(), articleDate.getDay()));
            articleDate.setYear(null);
            articleDate.setMonth(null);
            articleDate.setDay(null);
        }
        aboutVO.setArticleDates(articleDates);
        return new ResponseEntity<>(aboutVO, HttpStatus.OK);
    }
}
