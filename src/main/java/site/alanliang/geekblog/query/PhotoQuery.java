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
@ApiModel("照片查询条件")
@Data
public class PhotoQuery implements Serializable {
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;

    @ApiModelProperty("是否前台显示")
    private Boolean display;
}
