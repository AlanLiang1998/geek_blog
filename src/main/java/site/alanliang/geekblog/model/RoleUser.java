package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 12:26
 * Version 1.0
 **/
@Data
@TableName("sys_role_user")
public class RoleUser {
    @TableId
    private Long roleId;
    @TableId
    private Long userId;
}
