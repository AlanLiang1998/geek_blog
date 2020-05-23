package site.alanliang.geekblog.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/19 18:19
 * Version 1.0
 **/
@Data
public class ReplyVO {
    @NotNull
    private Long pid;

    private Long visitorId;
    @NotNull(groups = CommentReply.class)
    private Long articleId;

    @NotBlank(message = "回复内容不能为空")
    @Length(max = 80, message = "回复内容不能超过80个字符")
    private String reply;

    public interface CommentReply {

    }
}
