package site.alanliang.geekblog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ViewDateVO implements Serializable {
    private String date;

    private Integer viewCount;
}
