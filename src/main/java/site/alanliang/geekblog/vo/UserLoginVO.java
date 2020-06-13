package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/16 15:37
 * Version 1.0
 **/
@ApiModel("用户登录")
@Data
public class UserLoginVO implements Serializable {
    @ApiModelProperty("用户ID")
    @NotNull
    private Long userId;

    @ApiModelProperty("旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty("新密码")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message = "密码为6-16位（字母，数字，下划线，减号）的组合")
    private String newPassword;
}
