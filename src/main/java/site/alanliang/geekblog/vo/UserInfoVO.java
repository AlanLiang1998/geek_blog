package site.alanliang.geekblog.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/16 16:55
 * Version 1.0
 **/
@Data
public class UserInfoVO {
    @NotNull
    private Long id;

    @NotBlank(message = "昵称不能为空")
    @Length(min = 3, max = 16, message = "昵称长度为3-16个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\s·]+$", message = "\"昵称不能有特殊字符")
    private String nickname;

    private Boolean sex;

    @Email
    private String email;

    private String phone;
}
