package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.alanliang.geekblog.model.Article;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 16:47
 * Version 1.0
 **/
@ApiModel("文章详情页面数据")
@Data
public class ArticleDetailVO implements Serializable {
    @ApiModelProperty("文章详情")
    private Article article;

    @ApiModelProperty("上一篇文章概览")
    private Article prevPreview;

    @ApiModelProperty("下一篇文章概览")
    private Article nextPreview;
}
