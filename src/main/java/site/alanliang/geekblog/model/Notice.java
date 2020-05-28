package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_notice")
public class Notice implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull(message = "标题不能为空")
    @Length(max = 20, message = "标题长度在20个字符以内")
    private String title;

    @NotNull(message = "内容不能为空")
    @Length(max = 100, message = "内容长度在100个字符以内")
    private String content;

    @NotNull(message = "排序值不能为空")
    @Range(min = 1, max = 1024, message = "排序值在1-1024之间")
    private Integer sort;

    private Boolean display;

    private Date createTime;

    private Date updateTime;
}
