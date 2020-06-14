package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Descriptin 系统菜单
 * @Author AlanLiang
 * Date 2020/4/5 20:57
 * Version 1.0
 **/
@ApiModel("菜单")
@Data
@TableName("sys_menu")
public class Menu implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("外键:上级菜单ID")
    @NotNull(message = "上级菜单不能为空")
    private Long pid;

    @ApiModelProperty("标题")
    @NotBlank(message = "菜单标题不能为空")
    @Length(min = 2, max = 16, message = "菜单标题长度为2-16个字符")
    private String title;

    @ApiModelProperty("链接")
    private String href;

    @ApiModelProperty("标题")
    private String authority;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("排序值")
    @Min(value = 1, message = "排序值不能小于1")
    @Max(value = 1024, message = "排序值不能超过1024")
    private Integer sort;

    @ApiModelProperty("类型")
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @ApiModelProperty("是否显示")
    private Boolean status;

    @ApiModelProperty("链接跳转")
    private String target;

    @ApiModelProperty("标记")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public interface Table {
        String ID = "id";
        String TITLE = "title";
        String HREF = "href";
        String PID = "pid";
        String ICON = "icon";
        String AUTHORITY = "authority";
        String SORT = "sort";
        String TYPE = "type";
        String STATUS = "status";
        String REMARK = "remark";
        String TARGET = "target";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
