package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/21 9:53
 * Version 1.0
 **/
@ApiModel("访客登录")
@Data
public class VisitorLoginVO implements Serializable {
    @ApiModelProperty("身份信息(用户名或邮箱)")
    @NotNull
    private String certificate;

    @ApiModelProperty("密码")
    @NotNull
    private String password;
}
