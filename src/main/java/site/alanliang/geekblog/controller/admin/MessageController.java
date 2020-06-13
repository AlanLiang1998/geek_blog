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
import site.alanliang.geekblog.model.Message;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.query.MessageQuery;
import site.alanliang.geekblog.service.MessageService;
import site.alanliang.geekblog.utils.StringUtils;
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
 * Date 2020/5/19 20:24
 * Version 1.0
 **/
@Api(tags = "后台：留言管理")
@RestController
@RequestMapping("/admin/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @ApiOperation("查询留言")
    @PreAuthorize("hasAuthority('blog:message:query')")
    @OperationLog("查询留言")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       MessageQuery messageQuery) {
        Page<Message> pageInfo = messageService.listTableByPage(page, limit, messageQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("删除留言")
    @PreAuthorize("hasAuthority('blog:message:delete')")
    @OperationLog("删除留言")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        messageService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除留言")
    @PreAuthorize("hasAuthority('blog:message:delete')")
    @OperationLog("批量删除留言")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        messageService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("回复留言")
    @PreAuthorize("hasAuthority('blog:message:reply')")
    @OperationLog("回复留言")
    @PostMapping
    public JsonResult reply(@Validated @RequestBody ReplyVO replyVO, HttpServletRequest request, HttpSession session) {
        Message message = new Message();
        message.setContent(replyVO.getReply());
        message.setPid(replyVO.getPid());
        message.setOs(StringUtils.getClientOS(request));
        message.setBrowser(StringUtils.getBrowser(request));
        message.setRequestIp(StringUtils.getIp(request));
        message.setAddress(StringUtils.getCityInfo(message.getRequestIp()));
        message.setStatus(Constant.AUDIT_WAIT);
        message.setCreateTime(new Date());
        Object o = session.getAttribute("user");
        if (o != null) {
            User user = (User) o;
            message.setUserId(user.getId());
            message.setNickname(user.getNickname());
            message.setEmail(user.getEmail());
            message.setAvatar(user.getAvatar());
        }
        messageService.reply(message);
        return JsonResult.ok();
    }

    @ApiOperation("审核留言")
    @PreAuthorize("hasAuthority('blog:message:audit')")
    @OperationLog("审核留言")
    @PutMapping("/audit")
    public JsonResult audit(@Validated @RequestBody AuditVO auditVO) {
        messageService.audit(auditVO);
        return JsonResult.ok();
    }
}
