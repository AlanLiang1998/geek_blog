package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传成功后，存储结果
 *
 * @author Zheng Jie
 * @date 2018-12-31
 */
@ApiModel("七牛云文件")
@Data
@TableName("qiniu_content")
public class QiniuContent implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String name;

    /**
     * 空间名
     */
    @ApiModelProperty("空间名")
    private String bucket;

    /**
     * 大小
     */
    @ApiModelProperty("大小")
    private String size;

    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件后缀")
    private String suffix;

    /**
     * 空间类型：公开/私有
     */
    @ApiModelProperty("空间类型")
    private String type = "公开";

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("文件格式")
    private String fileType;
}
