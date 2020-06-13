package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("本地存储")
@Data
@TableName("sys_local_storage")
public class LocalStorage implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("真实文件名")
    private String realName;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件格式")
    private String suffix;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("大小")
    private String size;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
