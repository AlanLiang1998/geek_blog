package site.alanliang.geekblog.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.vo.ArticleDateVo;

import java.util.List;


/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 15:44
 * Version 1.0
 **/
@Data
public class ArchivesVo {
    private List<ArticleDateVo> articleDates;

    private Page<Article> pageInfo;
}
