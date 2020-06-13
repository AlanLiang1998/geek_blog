package site.alanliang.geekblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 11:33
 * Version 1.0
 **/
@ApiModel("友链查询条件")
@Data
public class LinkQuery implements Serializable {
    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;
}
