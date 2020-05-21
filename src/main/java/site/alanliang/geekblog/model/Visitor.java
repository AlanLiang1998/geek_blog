package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/20 22:01
 * Version 1.0
 **/
@Data
@TableName("t_visitor")
public class Visitor {
    @TableId(type = IdType.AUTO)
    private Long id;
    @Pattern(regexp = "^[A-Za-z0-9]{4,16}$", message = "用户名只能为数字或字母，长度为4-16个字符")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9]{6,16}$", message = "密码只能为数字或字母，长度为4-16个字符")
    private String password;
    @Pattern(regexp = "^[A-Za-z\\u4E00-\\u9FA5]{2,8}$", message = "昵称只能为字母或汉字，长度为2-8个字符")
    private String nickname;

    private String avatar;
    @Email(message = "邮箱格式不正确")
    private String email;
    @URL(message = "地址格式不正确")
    private String link;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}

