package site.alanliang.geekblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/3 16:25
 * Version 1.0
 **/
@ApiModel("文章查询条件")
@Data
public class ArticleQuery implements Serializable {
    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("类型[1:原创, 2:转载, 3:翻译]")
    private Integer type;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("是否发布")
    private Boolean published;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;

    @ApiModelProperty("是否置顶")
    private Boolean top;

    @ApiModelProperty("是否推荐")
    private Boolean recommend;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;
}
