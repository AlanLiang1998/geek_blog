package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel("七牛云配置")
@Data
@TableName("qiniu_config")
public class QiniuConfig implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @ApiModelProperty("密钥1")
    @NotBlank
    private String accessKey;

    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @ApiModelProperty("密钥2")
    @NotBlank
    private String secretKey;

    /**
     * 存储空间名称作为唯一的 Bucket 识别符
     */
    @ApiModelProperty("存储空间名称")
    @NotBlank
    private String bucket;

    /**
     * Zone表示与机房的对应关系
     * 华东	Zone.zone0()
     * 华北	Zone.zone1()
     * 华南	Zone.zone2()
     * 北美	Zone.zoneNa0()
     * 东南亚	Zone.zoneAs0()
     */
    @ApiModelProperty("地区")
    @NotBlank
    private String zone;

    /**
     * 外链域名，可自定义，需在七牛云绑定
     */
    @ApiModelProperty("外链域名")
    @NotBlank
    private String host;

    /**
     * 空间类型：公开/私有
     */
    @ApiModelProperty("空间类型")
    private String type = "公开";

    public interface Table {
        String ID = "id";
        String ACCESS_KEY = "access_key";
        String BUCKET = "bucket";
        String HOST = "host";
        String SECRET_KEY = "secret_key";
        String TYPE = "type";
        String ZONE = "zone";

    }
}
