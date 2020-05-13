package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/12 20:17
 * Version 1.0
 **/
@Data
@TableName("sys_role_menu")
public class RoleMenu {
    @TableId
    private Long roleId;
    @TableId
    private Long menuId;
}
