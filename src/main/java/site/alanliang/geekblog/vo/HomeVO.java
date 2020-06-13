package site.alanliang.geekblog.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.alanliang.geekblog.model.Article;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 13:01
 * Version 1.0
 **/
@ApiModel("前台主页数据")
@Data
public class HomeVO {
    @ApiModelProperty("置顶文章列表")
    private List<Article> topArticles;

    @ApiModelProperty("推荐文章列表")
    private List<Article> recommendArticles;

    @ApiModelProperty("文章分页")
    private Page<Article> pageInfo;
}
