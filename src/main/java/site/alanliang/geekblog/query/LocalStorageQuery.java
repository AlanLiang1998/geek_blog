package site.alanliang.geekblog.query;

import lombok.Data;

import java.util.Date;

@Data
public class LocalStorageQuery {
    private String name;

    private Date startDate;

    private Date endDate;
}
