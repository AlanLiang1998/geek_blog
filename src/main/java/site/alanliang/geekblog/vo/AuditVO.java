package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/23 10:11
 * Version 1.0
 **/
@ApiModel("审核VO")
@Data
public class AuditVO implements Serializable {
    @ApiModelProperty("审核对象ID")
    @NotNull
    private Long id;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    @NotNull
    private Integer status;
}
