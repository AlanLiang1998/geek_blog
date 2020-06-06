package site.alanliang.geekblog.query;

import lombok.Data;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/3 17:26
 * Version 1.0
 **/
@Data
public class TagQuery {
    private String name;

    private String startDate;

    private String endDate;
}
