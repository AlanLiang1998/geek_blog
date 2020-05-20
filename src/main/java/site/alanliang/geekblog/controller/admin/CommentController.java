package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.service.CommentService;
import site.alanliang.geekblog.utils.StringUtils;
import site.alanliang.geekblog.vo.ReplyVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/19 15:37
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public JsonResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<Comment> pageInfo = commentService.listTableByPage(page, limit);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @OperationLog("删除评论")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        commentService.removeById(id);
        return JsonResult.ok();
    }

    @OperationLog("批量删除评论")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        commentService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @OperationLog("回复评论")
    @PostMapping
    public JsonResult reply(@Validated @RequestBody ReplyVO replyVO, HttpServletRequest request, HttpSession session) {
        Comment comment = new Comment();
        comment.setPid(replyVO.getPid());
        comment.setContent(replyVO.getReply());
        comment.setBrowser(StringUtils.getBrowser(request));
        comment.setOs(StringUtils.getClientOS(request));
        comment.setRequestIp(StringUtils.getIp(request));
        comment.setAddress(StringUtils.getCityInfo(comment.getRequestIp()));
        comment.setStatus(1);
        comment.setCreateTime(new Date());
        Object o = session.getAttribute("user");
        if (o != null) {
            User user = (User) o;
            comment.setUserId(user.getId());
            comment.setNickname(user.getNickname());
            comment.setEmail(user.getEmail());
            comment.setAvatar(user.getAvatar());
        }
        commentService.reply(comment);
        return JsonResult.ok();
    }
}
