package site.alanliang.geekblog.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.common.ResultEnum;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.exception.NameNotUniqueException;
import site.alanliang.geekblog.utils.AjaxUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Descriptin 全局异常处理
 * @Author AlanLiang
 * Date 2020/4/6 17:09
 * Version 1.0
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NameNotUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult handleNameNotUniqueException(NameNotUniqueException exception) {
        log.error("参数验证失败", exception);
        return JsonResult.build(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("参数验证失败", exception);
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();
        String message = String.format("%s : %s", field, defaultMessage);
        return JsonResult.build(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult handleBadRequestException(BadRequestException exception) {
        log.error(exception.getMessage());
        return JsonResult.build(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleGlobalException(HttpServletRequest request, Exception exception) {
        log.error("Request URL : {}, Exception : {}", request.getRequestURL(), exception.getMessage());
        if (AjaxUtil.isAjaxRequest(request)) {
            return JsonResult.build(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMessage());
        }
        return new ModelAndView("error/500");
    }
}
