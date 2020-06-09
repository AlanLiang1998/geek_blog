package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 10:33
 * Version 1.0
 **/
@Data
@TableName("sys_role")
public class Role {
    @TableId(type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 16, message = "角色名称长度为2-16个字符")
    private String roleName;
    @Length(max = 100, message = "描述长度在100个字符以内")
    private String description;
    @Max(value = 1024, message = "角色级别数值不能超过1024")
    @Min(value = 1, message = "角色级别数值不能小于1")
    private Integer rank;

    private String color;

    private Date createTime;

    private Date updateTime;

    @NotEmpty(message = "权限不能为空")
    @TableField(exist = false)
    private List<Long> menuIdList;

    @TableField(exist = false)
    private Integer userCount;
}
