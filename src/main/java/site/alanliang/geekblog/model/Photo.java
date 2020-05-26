package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_photo")
public class Photo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "照片url不能为空")
    private String url;
    @NotNull(message = "描述不能为空")
    private String description;
    @Range(min = 1, max = 1024, message = "排序值范围在1-1024之间")
    private Integer sort;

    private Boolean display;

    private Date createTime;

    private Date updateTime;
}
