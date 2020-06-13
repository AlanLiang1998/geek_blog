package site.alanliang.geekblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 10:13
 * Version 1.0
 **/
@ApiModel("友情链接")
@Data
@TableName("t_link")
public class Link implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("昵称")
    @NotNull(message = "昵称不能为空")
    @Length(min = 1, max = 10, message = "昵称长度为1-10个字符")
    private String nickname;

    @ApiModelProperty("头像地址")
    @NotNull(message = "头像不能为空")
    @URL
    private String avatar;

    @ApiModelProperty("简介")
    @NotNull(message = "简介不能为空")
    @Length(min = 1, max = 30, message = "简介长度在30个字符以内")
    private String introduction;

    @ApiModelProperty("网址")
    @NotNull(message = "网址不能为空")
    @URL
    private String link;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;

    @ApiModelProperty("排序值")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
