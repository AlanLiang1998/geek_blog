package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin 文章
 * @Author AlanLiang
 * Date 2020/4/5 20:53
 * Version 1.0
 **/

@ApiModel("文章")
@Data
@TableName("t_article")
@JsonIgnoreProperties("handler")
public class Article implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("标题")
    @NotBlank(message = "文章标题不能为空")
    @Length(max = 100, message = "文章标题长度不能超过100")
    private String title;

    @ApiModelProperty("摘要")
    private String summary;

    @ApiModelProperty("HTML内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;

    @ApiModelProperty("markdown内容")
    private String textContent;

    @ApiModelProperty("封面URL")
    private String cover;

    @ApiModelProperty("浏览量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("是否开启赞赏")
    private Boolean appreciable;

    @ApiModelProperty("是否开启评论")
    private Boolean commentable;

    @ApiModelProperty("是否发布")
    private Boolean published;

    @ApiModelProperty("是否置顶")
    private Boolean top;

    @ApiModelProperty("是否推荐")
    private Boolean recommend;

    @ApiModelProperty("外键:作者ID")
    private Long authorId;

    @ApiModelProperty("外键:分类ID")
    @NotNull(message = "请选择一个分类")
    private Long categoryId;

    @ApiModelProperty("类型[1:原创, 2:转载, 3:翻译]")
    private Integer type;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("排序值")
    private Integer sort;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;

    @ApiModelProperty("分类")
    @TableField(exist = false)
    private Category category;

    @ApiModelProperty("标签ID列表")
    @TableField(exist = false)
    private List<Tag> tagList;

    @ApiModelProperty("作者")
    @TableField(exist = false)
    private User author;
}
