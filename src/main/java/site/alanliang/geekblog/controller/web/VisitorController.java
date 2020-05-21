package site.alanliang.geekblog.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.model.Visitor;
import site.alanliang.geekblog.service.VisitorService;
import site.alanliang.geekblog.utils.MD5Util;
import site.alanliang.geekblog.vo.VisitorLoginVO;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/20 22:05
 * Version 1.0
 **/
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @PostMapping
    public ResponseEntity<Object> save(@Validated @RequestBody Visitor visitor) {
        visitor.setPassword(MD5Util.code(visitor.getPassword()));
        visitor.setAvatar(Constant.DEFAULT_AVATAR);
        visitor.setCreateTime(new Date());
        visitor.setUpdateTime(visitor.getCreateTime());
        visitor.setStatus(Constant.VISITOR_ENABLE);
        visitorService.save(visitor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated @RequestBody VisitorLoginVO visitorLoginVO) {
        visitorLoginVO.setPassword(MD5Util.code(visitorLoginVO.getPassword()));
        return new ResponseEntity<>(visitorService.login(visitorLoginVO), HttpStatus.OK);
    }
}
