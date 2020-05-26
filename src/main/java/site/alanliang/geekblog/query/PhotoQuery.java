package site.alanliang.geekblog.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 11:33
 * Version 1.0
 **/
@Data
public class PhotoQuery implements Serializable {
    private String description;

    private Date startDate;

    private Date endDate;

    private Boolean display;
}
