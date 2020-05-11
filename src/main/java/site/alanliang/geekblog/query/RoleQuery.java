package site.alanliang.geekblog.query;

import lombok.Data;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 9:10
 * Version 1.0
 **/
@Data
public class RoleQuery {
    private String roleName;

    private String description;

    private Date startDate;

    private Date endDate;
}
