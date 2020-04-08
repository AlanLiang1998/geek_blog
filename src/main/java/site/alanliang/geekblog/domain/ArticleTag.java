package site.alanliang.geekblog.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Descriptin 文章标签关系
 * @Author AlanLiang
 * Date 2020/4/5 20:59
 * Version 1.0
 **/
@Data
@TableName("t_article_tag")
public class ArticleTag {
    @TableId
    private Long articleId;
    @TableId
    private Long tagId;
}
