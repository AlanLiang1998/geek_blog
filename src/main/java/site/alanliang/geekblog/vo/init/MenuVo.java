package site.alanliang.geekblog.vo.init;

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
public class MenuVo {
    private Long id;

    private Long pid;

    private String title;

    private String icon;

    private String href;

    private String target;

    private List<MenuVo> child;
}