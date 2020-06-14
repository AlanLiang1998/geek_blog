package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel("照片")
@Data
@TableName("t_photo")
public class Photo implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("照片url")
    @NotNull(message = "照片url不能为空")
    private String url;

    @ApiModelProperty("描述")
    @NotNull(message = "描述不能为空")
    private String description;

    @ApiModelProperty("排序值")
    @Range(min = 1, max = 1024, message = "排序值范围在1-1024之间")
    private Integer sort;

    @ApiModelProperty("是否前台显示")
    private Boolean display;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public interface Table {
        String ID = "id";
        String URL = "url";
        String DESCRIPTION = "description";
        String SORT = "sort";
        String DISPLAY = "display";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
