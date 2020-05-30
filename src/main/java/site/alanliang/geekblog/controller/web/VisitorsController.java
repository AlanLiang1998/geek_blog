package site.alanliang.geekblog.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.AccessLog;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.model.Visitor;
import site.alanliang.geekblog.service.VisitorService;
import site.alanliang.geekblog.utils.MD5Util;
import site.alanliang.geekblog.vo.VisitorLoginVO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/20 22:05
 * Version 1.0
 **/
@RestController
@RequestMapping("/visitor")
public class VisitorsController {

    @Autowired
    private VisitorService visitorService;

    /**
     * 将日期格式的String类型转为Date类型
     *
     * @param binder 数据绑定
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        String pattern = "yyyy-MM-dd";
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat(pattern), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @AccessLog("访客注册")
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

    @AccessLog("访客登录")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated @RequestBody VisitorLoginVO visitorLoginVO) {
        visitorLoginVO.setPassword(MD5Util.code(visitorLoginVO.getPassword()));
        return new ResponseEntity<>(visitorService.login(visitorLoginVO), HttpStatus.OK);
    }

}
