package site.alanliang.geekblog.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.service.CommentService;
import site.alanliang.geekblog.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/17 9:13
 * Version 1.0
 **/
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public JsonResult save(@Validated @RequestBody Comment comment, HttpServletRequest request) {
        comment.setCreateTime(new Date());
        comment.setBrowser(StringUtils.getBrowser(request));
        comment.setOs(StringUtils.getClientOS(request));
        comment.setRequestIp(StringUtils.getIp(request));
        comment.setAddress(StringUtils.getCityInfo(comment.getRequestIp()));
        comment.setStatus(Constant.COMMENT_WAIT);
        commentService.save(comment);
        return JsonResult.ok();
    }

    @GetMapping("/listByArticleId/{articleId}")
    public ResponseEntity<Object> listByArticleId(@PathVariable("articleId") Long articleId,
                                                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                  @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Comment> page = new Page<>(current, size);
        Page<Comment> pageInfo = commentService.listByArticleId(articleId, current, size);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }


}
