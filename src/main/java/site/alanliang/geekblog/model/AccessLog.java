package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 11:01
 * Version 1.0
 **/
@ApiModel("访问日志")
@Data
@NoArgsConstructor
@TableName("sys_access_log")
public class AccessLog implements Serializable {

    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("异常详情")
    private byte[] exceptionDetail;

    @ApiModelProperty("日志类型")
    private String logType;

    @ApiModelProperty("方法名称")
    private String method;

    @ApiModelProperty("参数名称")
    private String params;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("请求耗时")
    private Long time;

    @ApiModelProperty("访客用户名")
    private String username;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("IP来源")
    private String address;

    @ApiModelProperty("请求结果[0:失败, 1:成功]")
    private Integer status;

    public AccessLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }

    public interface Table {
        String ID = "id";
        String CREATE_TIME = "create_time";
        String DESCRIPTION = "description";
        String EXCEPTION_DETAIL = "exception_detail";
        String LOG_TYPE = "log_type";
        String METHOD = "method";
        String PARAMS = "params";
        String REQUEST_IP = "request_ip";
        String TIME = "time";
        String USERNAME = "username";
        String BROWSER = "browser";
        String ADDRESS = "address";
        String STATUS = "status";
    }
}
