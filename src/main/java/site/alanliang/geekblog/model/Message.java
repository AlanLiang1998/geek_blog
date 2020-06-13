package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin 留言
 * @Author AlanLiang
 * Date 2020/4/5 21:01
 * Version 1.0
 **/
@ApiModel("留言")
@Data
@TableName("t_message")
public class Message implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("外键:父级ID")
    private Long pid;

    @ApiModelProperty("外键:用户ID")
    private Long userId;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty("内容")
    @NotBlank(message = "评论内容不能为空")
    @Length(max = 80, message = "评论内容不能超过80个字符")
    private String content;

    @ApiModelProperty("网址")
    private String link;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    @Email
    private String email;

    @ApiModelProperty("客户端浏览器")
    private String browser;

    @ApiModelProperty("客户端系统")
    private String os;

    @ApiModelProperty("IP来源")
    private String address;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(exist = false)
    private List<Message> children;

    @TableField(exist = false)
    private Message parentMessage;
}
