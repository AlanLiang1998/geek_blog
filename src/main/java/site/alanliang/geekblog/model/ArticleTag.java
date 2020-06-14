package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin 文章标签关系
 * @Author AlanLiang
 * Date 2020/4/5 20:59
 * Version 1.0
 **/
@ApiModel("文章标签关联")
@Data
@TableName("t_article_tag")
public class ArticleTag implements Serializable {
    @ApiModelProperty("主键:文章ID")
    @TableId
    private Long articleId;

    @ApiModelProperty("主键:标签ID")
    @TableId
    private Long tagId;

    public interface Table {
        String ARTICLE_ID = "article_id";
        String TAG_ID = "tag_id";
    }
}
