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
import site.alanliang.geekblog.model.Message;
import site.alanliang.geekblog.service.MessageService;
import site.alanliang.geekblog.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/19 20:40
 * Version 1.0
 **/
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<Object> listByPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                             @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Message> pageInfo = messageService.listByPage(current, size);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

    @PostMapping
    public JsonResult save(@Validated @RequestBody Message message, HttpServletRequest request) {
        message.setCreateTime(new Date());
        message.setAvatar(Constant.DEFAULT_AVATAR);
        message.setBrowser(StringUtils.getBrowser(request));
        message.setOs(StringUtils.getClientOS(request));
        message.setRequestIp(StringUtils.getIp(request));
        message.setAddress(StringUtils.getCityInfo(message.getRequestIp()));
        message.setStatus(1);
        messageService.save(message);
        return JsonResult.ok();
    }
}
