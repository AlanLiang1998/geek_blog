package site.alanliang.geekblog.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 22:13
 * Version 1.0
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuSelectVO {
    private String name;

    private Long value;

    private Long pid;

    private List<MenuSelectVO> children;
}