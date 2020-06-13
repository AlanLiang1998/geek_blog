package site.alanliang.geekblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 19:17
 * Version 1.0
 **/
@ApiModel("归档查询条件")
@Data
public class ArchivesQuery implements Serializable {
    @ApiModelProperty("日期统计类型[1:按日统计, 2:按月统计, 3:按年统计]")
    private Integer dateFilterType;
}
