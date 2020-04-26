package site.alanliang.geekblog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 11:01
 * Version 1.0
 **/
@TableName("sys_access_log")
@Data
public class SysAccessLog {
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

    public SysAccessLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
