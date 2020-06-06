package site.alanliang.geekblog.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 11:33
 * Version 1.0
 **/
@Data
public class PhotoQuery implements Serializable {
    private String description;

    private String startDate;

    private String endDate;

    private Boolean display;
}
