package site.alanliang.geekblog.query;

import lombok.Data;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 9:10
 * Version 1.0
 **/
@Data
public class UserQuery {
    private String username;

    private String email;

    private String startDate;

    private String endDate;
}
