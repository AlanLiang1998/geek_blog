package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Descriptin 文章评论
 * @Author AlanLiang
 * Date 2020/4/5 21:01
 * Version 1.0
 **/
@Data
@TableName("t_comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Date createTime;
}
