package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long pid;

    private Long articleId;

    private Long userId;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "评论内容不能为空")
    private String content;

    private String link;

    private String avatar;
    @Email
    private String email;

    private Integer status;

    private Date createTime;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private Comment parentComment;

    private String browser;

    private String os;

    private String address;

    private String requestIp;
}
