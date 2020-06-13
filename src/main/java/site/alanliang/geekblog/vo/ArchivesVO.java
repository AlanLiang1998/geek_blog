package site.alanliang.geekblog.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.alanliang.geekblog.model.Article;

import java.io.Serializable;
import java.util.List;


/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 15:44
 * Version 1.0
 **/
@ApiModel("归档页面数据")
@Data
public class ArchivesVO implements Serializable {
    @ApiModelProperty("文章日期统计")
    private List<ArticleDateVO> articleDates;

    @ApiModelProperty("文章分类")
    private Page<Article> pageInfo;
}
