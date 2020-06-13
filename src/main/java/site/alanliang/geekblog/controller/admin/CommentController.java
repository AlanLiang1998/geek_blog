package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.query.CommentQuery;
import site.alanliang.geekblog.service.CommentService;
import site.alanliang.geekblog.utils.StringUtils;
import site.alanliang.geekblog.utils.UserInfoUtil;
import site.alanliang.geekblog.vo.AuditVO;
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
@Api(tags = "后台：评论管理")
@RestController
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("查询评论")
    @PreAuthorize("hasAuthority('blog:comment:query')")
    @OperationLog("查询评论")
    @GetMapping
    public JsonResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                      CommentQuery commentQuery) {
        Page<Comment> pageInfo = commentService.listTableByPage(page, limit, commentQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("删除评论")
    @PreAuthorize("hasAuthority('blog:comment:delete')")
    @OperationLog("删除评论")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        commentService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除评论")
    @PreAuthorize("hasAuthority('blog:comment:delete')")
    @OperationLog("批量删除评论")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        commentService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("回复评论")
    @PreAuthorize("hasAuthority('blog:comment:reply')")
    @OperationLog("回复评论")
    @PostMapping
    public JsonResult reply(@Validated({ReplyVO.CommentReply.class}) @RequestBody ReplyVO replyVO, HttpServletRequest request, HttpSession session) {
        Comment comment = new Comment();
        comment.setPid(replyVO.getPid());
        comment.setArticleId(replyVO.getArticleId());
        comment.setContent(replyVO.getReply());
        comment.setBrowser(StringUtils.getBrowser(request));
        comment.setOs(StringUtils.getClientOS(request));
        comment.setRequestIp(StringUtils.getIp(request));
        comment.setAddress(StringUtils.getCityInfo(comment.getRequestIp()));
        comment.setStatus(Constant.AUDIT_WAIT);
        comment.setCreateTime(new Date());
        comment.setUserId(UserInfoUtil.getId());
        commentService.reply(comment);
        return JsonResult.ok();
    }

    @ApiOperation("审核评论")
    @PreAuthorize("hasAuthority('blog:comment:audit')")
    @OperationLog("审核评论")
    @PutMapping("/audit")
    public JsonResult audit(@Validated @RequestBody AuditVO auditVO) {
        commentService.audit(auditVO);
        return JsonResult.ok();
    }
}
