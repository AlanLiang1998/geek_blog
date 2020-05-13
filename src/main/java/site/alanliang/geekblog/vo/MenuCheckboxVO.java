package site.alanliang.geekblog.vo;

import lombok.Data;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/12 19:12
 * Version 1.0
 **/
@Data
public class MenuCheckboxVO {
    private Long id;

    private Long parentId;

    private String title;

    private String checkArr;

    private List<MenuCheckboxVO> children;
}
