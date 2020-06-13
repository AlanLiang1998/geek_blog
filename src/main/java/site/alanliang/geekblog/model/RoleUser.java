package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 12:26
 * Version 1.0
 **/
@ApiModel("角色用户关联")
@Data
@TableName("sys_role_user")
public class RoleUser implements Serializable {
    @ApiModelProperty("主键:角色ID")
    @TableId
    private Long roleId;

    @ApiModelProperty("主键:用户ID")
    @TableId
    private Long userId;
}
