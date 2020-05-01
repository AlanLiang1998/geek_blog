package site.alanliang.geekblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Descriptin 文章标签
 * @Author AlanLiang
 * Date 2020/4/5 21:02
 * Version 1.0
 **/
@Data
@TableName("t_tag")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private Integer articleCount;
}
