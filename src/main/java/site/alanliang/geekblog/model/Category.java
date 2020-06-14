package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Descriptin 文章分类
 * @Author AlanLiang
 * Date 2020/4/5 21:00
 * Version 1.0
 **/
@ApiModel("分类")
@Data
@TableName("t_category")
public class Category implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty("描述")
    @NotBlank(message = "分类简介不能为空")
    private String introduction;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("是否前台实现")
    private Boolean display;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("关联文章数量")
    @TableField(exist = false)
    private Integer articleCount;

    public interface Table {
        String ID = "id";
        String NAME = "name";
        String DISPLAY = "display";
        String INTRODUCTION = "introduction";
        String COLOR = "color";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
