package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Descriptin 系统菜单
 * @Author AlanLiang
 * Date 2020/4/5 20:57
 * Version 1.0
 **/
@Data
@TableName("sys_menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Long id;
    @NotNull(message = "上级菜单不能为空")
    private Long pid;
    @NotBlank(message = "菜单标题不能为空")
    @Length(min = 2, max = 16, message = "菜单标题长度为2-16个字符")
    private String title;

    private String href;

    private String authority;

    private String icon;
    @Min(value = 1, message = "排序值不能小于1")
    @Max(value = 1024, message = "排序值不能超过1024")
    private Integer sort;
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    private Boolean status;

    private String target;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
