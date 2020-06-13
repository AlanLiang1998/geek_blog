package site.alanliang.geekblog.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 22:13
 * Version 1.0
 **/
@ApiModel("菜单")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuVO implements Serializable {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("父级菜单ID")
    private Long pid;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("链接")
    private String href;

    @ApiModelProperty("页面跳转方式")
    private String target;

    @ApiModelProperty("子级菜单列表")
    private List<MenuVO> child;
}