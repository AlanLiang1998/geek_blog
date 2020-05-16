package site.alanliang.geekblog.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/16 15:37
 * Version 1.0
 **/
@Data
public class PasswordVO {
    @NotNull
    private Long userId;
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message = "密码为6-16位（字母，数字，下划线，减号）的组合")
    private String newPassword;
}
