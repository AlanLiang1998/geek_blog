package site.alanliang.geekblog.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/21 9:53
 * Version 1.0
 **/
@Data
public class VisitorLoginVO {
    @NotNull
    private String certificate;
    @NotNull
    private String password;
}
