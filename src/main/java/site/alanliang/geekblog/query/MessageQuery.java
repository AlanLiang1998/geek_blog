package site.alanliang.geekblog.query;

import lombok.Data;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/22 17:37
 * Version 1.0
 **/
@Data
public class MessageQuery {
    private String nickname;

    private Date startDate;

    private Date endDate;

    private Integer status;
}
