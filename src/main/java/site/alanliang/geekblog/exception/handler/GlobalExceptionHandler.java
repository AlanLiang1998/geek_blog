package site.alanliang.geekblog.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import site.alanliang.geekblog.exception.AssociationExistException;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.utils.AjaxUtil;
import site.alanliang.geekblog.utils.ThrowableUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Descriptin 全局异常处理
 * @Author AlanLiang
 * Date 2020/4/6 17:09
 * Version 1.0
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        if (AjaxUtil.isAjaxRequest(request)) {
            return buildResponseEntity(ApiError.error("您没有权限执行该操作"));
        }
        return new ModelAndView("error/403");
    }

    @ExceptionHandler(EntityExistException.class)
    public ResponseEntity<ApiError> handleEntityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    @ExceptionHandler(AssociationExistException.class)
    public ResponseEntity<ApiError> handleAssociationExistException(AssociationExistException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if (msg.equals(message)) {
            message = str[1] + ":" + message;
        }
        return buildResponseEntity(ApiError.error(message));
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(BadRequestException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getStatus(), e.getMessage()));
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsException(BadCredentialsException e) {
        // 打印堆栈信息
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return buildResponseEntity(ApiError.error(message));
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public Object handleException(HttpServletRequest request, Throwable e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        if (AjaxUtil.isAjaxRequest(request)) {
            return buildResponseEntity(ApiError.error(e.getMessage()));
        } else {
            return new ModelAndView("error/500");
        }
    }

    /**
     * 统一返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
