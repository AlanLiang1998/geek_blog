package site.alanliang.geekblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.TableResult;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.model.Message;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.service.MessageService;
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
 * Date 2020/5/19 20:24
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<Message> pageInfo = messageService.listTableByPage(page, limit);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @OperationLog("删除留言")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        messageService.removeById(id);
        return JsonResult.ok();
    }

    @OperationLog("批量删除留言")
    @DeleteMapping
    public JsonResult batchRemove(@NotEmpty @RequestBody List<Long> idList) {
        messageService.removeByIdList(idList);
        return JsonResult.ok();
    }

    @OperationLog("回复评论")
    @PostMapping
    public JsonResult reply(@Validated @RequestBody ReplyVO replyVO, HttpServletRequest request, HttpSession session) {
        Message message = new Message();
        message.setContent(replyVO.getReply());
        message.setPid(replyVO.getPid());
        message.setOs(StringUtils.getClientOS(request));
        message.setBrowser(StringUtils.getBrowser(request));
        message.setRequestIp(StringUtils.getIp(request));
        message.setAddress(StringUtils.getCityInfo(message.getRequestIp()));
        message.setStatus(1);
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
}
