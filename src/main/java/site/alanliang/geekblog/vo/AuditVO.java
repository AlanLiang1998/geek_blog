package site.alanliang.geekblog.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/23 10:11
 * Version 1.0
 **/
@Data
public class AuditVO {
    @NotNull
    private Long id;
    @NotNull
    private Integer status;
}
