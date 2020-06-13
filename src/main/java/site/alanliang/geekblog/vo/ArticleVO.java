package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.alanliang.geekblog.model.Article;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/8 8:57
 * Version 1.0
 **/
@ApiModel("文章VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleVO extends Article implements Serializable {
    @ApiModelProperty("文章标签列表")
    @Size(min = 1,message = "请选择至少一个标签")
    private List<Long> tagIdList;
}
