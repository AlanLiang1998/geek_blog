package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("浏览器日期统计")
@Data
public class ViewDateVO implements Serializable {
    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("浏览量")
    private Integer viewCount;
}
