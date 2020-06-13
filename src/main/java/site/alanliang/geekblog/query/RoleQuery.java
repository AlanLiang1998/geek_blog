package site.alanliang.geekblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 9:10
 * Version 1.0
 **/
@ApiModel("角色查询条件")
@Data
public class RoleQuery implements Serializable {
    @ApiModelProperty("名称")
    private String roleName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;
}
