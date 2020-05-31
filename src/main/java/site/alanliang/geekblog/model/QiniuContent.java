package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传成功后，存储结果
 *
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
@TableName("qiniu_content")
public class QiniuContent implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * 大小
     */
    private String size;

    /**
     * 文件地址
     */
    private String url;


    private String suffix;

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

    private Date updateTime;

    private String fileType;
}
