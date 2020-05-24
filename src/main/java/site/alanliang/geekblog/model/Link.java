package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 10:13
 * Version 1.0
 **/
@Data
@TableName("t_link")
public class Link {
    @TableId(type = IdType.AUTO)
    private Long id;
    @Length(min = 1, max = 10, message = "昵称长度为1-10个字符")
    private String nickname;
    @URL
    private String avatar;
    @Length(min = 1, max = 30, message = "简介长度在30个字符以内")
    private String introduction;
    @URL
    private String link;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
