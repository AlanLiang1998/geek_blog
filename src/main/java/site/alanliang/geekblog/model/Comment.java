package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
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
@Data
@TableName("t_comment")
@JsonIgnoreProperties("handler")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long pid;

    private Long articleId;

    @NotNull
    private Long visitorId;

    private Long userId;

    @NotBlank(message = "评论内容不能为空")
    @Length(max = 80, message = "评论内容不能超过80个字符")
    private String content;

    private Integer status;

    private Date createTime;

    @TableField(exist = false)
    private List<Comment> children;

    private String parentNickname;

    private String browser;

    private String os;

    private String address;

    private String requestIp;

    @TableField(exist = false)
    private Visitor visitor;
}
