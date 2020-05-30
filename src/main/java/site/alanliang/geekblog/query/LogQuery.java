package site.alanliang.geekblog.query;

import lombok.Data;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 11:01
 * Version 1.0
 **/
@Data
public class LogQuery {
    private String description;

    private String requestIp;

    private String browser;

    private Date startDate;

    private Date endDate;

    private String address;

    private Integer timeRank;

    private String username;
}
