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
 * @Descriptin 文章评论
 * @Author AlanLiang
 * Date 2020/4/5 21:01
 * Version 1.0
 **/
@ApiModel("评论")
@Data
@TableName("t_comment")
@JsonIgnoreProperties("handler")
public class Comment implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("外键:父级ID")
    private Long pid;

    @ApiModelProperty("外键:文章ID")
    private Long articleId;

    @ApiModelProperty("外键:访客ID")
    @NotNull
    private Long visitorId;

    @ApiModelProperty("外键:用户ID")
    private Long userId;

    @ApiModelProperty("评论内容")
    @NotBlank(message = "评论内容不能为空")
    @Length(max = 80, message = "评论内容不能超过80个字符")
    private String content;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("回复昵称")
    private String parentNickname;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("客户端系统")
    private String os;

    @ApiModelProperty("IP来源")
    private String address;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("访客")
    @TableField(exist = false)
    private Visitor visitor;

    @ApiModelProperty("回复评论列表")
    @TableField(exist = false)
    private List<Comment> children;
}
