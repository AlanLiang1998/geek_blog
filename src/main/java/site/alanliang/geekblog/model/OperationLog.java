package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_operation_log")
@Data
@NoArgsConstructor
public class OperationLog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Date createTime;

    private String description;

    private byte[] exceptionDetail;

    private String logType;

    private String method;

    private String params;

    private String requestIp;

    private Long time;

    private String username;

    private String browser;

    private String address;

    private Integer status;

    public OperationLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
