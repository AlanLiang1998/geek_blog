package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin 留言
 * @Author AlanLiang
 * Date 2020/4/5 21:01
 * Version 1.0
 **/
@Data
@TableName("t_message")
public class Message {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long pid;

    private Long userId;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "评论内容不能为空")
    @Length(max = 80, message = "评论内容不能超过80个字符")
    private String content;

    private String link;

    private String avatar;
    @Email
    private String email;

    private Integer status;

    private Date createTime;

    @TableField(exist = false)
    private List<Message> children;

    @TableField(exist = false)
    private Message parentMessage;

    private String browser;

    private String os;

    private String address;

    private String requestIp;
}
