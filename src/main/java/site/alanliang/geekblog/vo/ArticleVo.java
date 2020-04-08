package site.alanliang.geekblog.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import site.alanliang.geekblog.domain.Category;
import site.alanliang.geekblog.domain.Tag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/8 16:31
 * Version 1.0
 **/
@Data
public class ArticleVo {
    private Long id;

    private String title;

    private Integer views;

    private Integer likes;

    private Integer comments;

    private Boolean published;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer type;

    private Category category;

    private List<Tag> tagList;
}
