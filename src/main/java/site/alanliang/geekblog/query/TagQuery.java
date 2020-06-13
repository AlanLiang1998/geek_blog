package site.alanliang.geekblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/3 17:26
 * Version 1.0
 **/
@ApiModel("标签查询条件")
@Data
public class TagQuery implements Serializable {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;
}
