package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/20 22:01
 * Version 1.0
 **/
@ApiModel("访客")
@Data
@TableName("t_visitor")
public class Visitor implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    @Pattern(regexp = "^[A-Za-z0-9]{4,16}$", message = "用户名只能为数字或字母，长度为4-16个字符")
    private String username;

    @ApiModelProperty("密码")
    @Pattern(regexp = "^[A-Za-z0-9]{6,16}$", message = "密码只能为数字或字母，长度为4-16个字符")
    private String password;

    @ApiModelProperty("昵称")
    @Pattern(regexp = "^[A-Za-z\\u4E00-\\u9FA5]{2,8}$", message = "昵称只能为字母或汉字，长度为2-8个字符")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("网址")
    @URL(message = "地址格式不正确")
    private String link;

    @ApiModelProperty("启用状态[0:停用, 1:启用]")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public interface Table {
        String ID = "id";
        String USERNAME = "username";
        String PASSWORD = "password";
        String NICKNAME = "nickname";
        String AVATAR = "avatar";
        String EMAIL = "email";
        String LINK = "link";
        String STATUS = "status";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}

