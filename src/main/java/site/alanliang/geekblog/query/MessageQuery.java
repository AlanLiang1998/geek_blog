package site.alanliang.geekblog.query;

import lombok.Data;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/22 17:37
 * Version 1.0
 **/
@Data
public class MessageQuery {
    private String nickname;

    private String startDate;

    private String endDate;

    private Integer status;
}
