package site.alanliang.geekblog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Descriptin 系统菜单
 * @Author AlanLiang
 * Date 2020/4/5 20:57
 * Version 1.0
 **/
@Data
@TableName("sys_menu")
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String href;

    private Long pid;

    private String icon;

    private String target;

    private Integer sort;

    private Boolean status;

    private String remark;

    private String createTime;

    private String updateTime;
}
