package site.alanliang.geekblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Descriptin 文章分类
 * @Author AlanLiang
 * Date 2020/4/5 21:00
 * Version 1.0
 **/
@Data
@TableName("t_category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    private Boolean display;
    @NotBlank(message = "分类简介不能为空")
    private String introduction;

    private Date createTime;

    private Date updateTime;
    @TableField(exist = false)
    private Integer articleCount;
}
