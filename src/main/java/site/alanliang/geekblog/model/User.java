package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Descriptin 用户
 * @Author AlanLiang
 * Date 2020/4/5 20:50
 * Version 1.0
 **/
@Data
@TableName("sys_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "用户名不能为空")
    @Length(min = 3, max = 16, message = "用户名长度为3-16个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\s·]+$", message = "用户名不能有特殊字符")
    private String username;

    private String password;
    @NotBlank(message = "昵称不能为空")
    @Length(min = 3, max = 16, message = "昵称长度为3-16个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\s·]+$", message = "\"昵称不能有特殊字符")
    private String nickname;

    private String avatar;

    private Boolean sex;
    @Email
    private String email;

    private String phone;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(exist = false)
    @NotNull(message = "请为用户选择一个角色")
    private Long roleId;
}
