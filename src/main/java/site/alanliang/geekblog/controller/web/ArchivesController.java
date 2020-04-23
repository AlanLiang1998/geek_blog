package site.alanliang.geekblog.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.javafx.binding.IntegerConstant;
import com.sun.javafx.binding.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.utils.DateUtil;
import site.alanliang.geekblog.vo.ArticleDateVO;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 9:12
 * Version 1.0
 **/
@RestController
public class ArchivesController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/archives/calendar-data")
    public ResponseEntity<Object> calendarData(@RequestParam(value = "type", required = false) Integer dateFilterType) {
        if (dateFilterType == null) {
            dateFilterType = Constant.FILTER_BY_DAY;
        }
        List<ArticleDateVO> articleDates = articleService.countArticleByDate(dateFilterType);

        for (ArticleDateVO articleDate : articleDates) {
            articleDate.setDate(DateUtil.formatDate(articleDate.getYear(), articleDate.getMonth(), articleDate.getDay()));
            articleDate.setYear(null);
            articleDate.setMonth(null);
            articleDate.setDay(null);
        }
        return new ResponseEntity<>(articleDates, HttpStatus.OK);
    }

    @GetMapping("/archives")
    public ResponseEntity<Object> archives(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size,
                                           ArticleQuery articleQuery) {

        Page<Article> pageInfo = articleService.listPageArticlePreviewByDate(current, size, articleQuery);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

}
