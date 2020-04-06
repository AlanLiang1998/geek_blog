package site.alanliang.geekblog.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.ResultEnum;
import site.alanliang.geekblog.utils.AjaxUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Descriptin 全局异常处理
 * @Author AlanLiang
 * Date 2020/4/6 17:09
 * Version 1.0
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleGlobalException(HttpServletRequest request, Exception e) {
        log.error("Request URL : {}, Exception : {}", request.getRequestURL(), e.getMessage());
        if (AjaxUtil.isAjaxRequest(request)) {
            return JsonResult.build(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMessage());
        }
        return new ModelAndView("error/500");
    }
}
