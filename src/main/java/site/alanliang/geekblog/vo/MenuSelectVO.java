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
@ApiModel("下拉菜单")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuSelectVO implements Serializable {
    @ApiModelProperty("标题")
    private String name;

    @ApiModelProperty("ID")
    private Long value;

    @ApiModelProperty("父级菜单ID")
    private Long pid;

    @ApiModelProperty("子级菜单列表")
    private List<MenuSelectVO> children;
}