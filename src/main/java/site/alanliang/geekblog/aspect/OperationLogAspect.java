package site.alanliang.geekblog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.model.OperationLog;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.service.OperationLogService;
import site.alanliang.geekblog.utils.RequestHolder;
import site.alanliang.geekblog.utils.StringUtils;
import site.alanliang.geekblog.utils.ThrowableUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Descriptin 操作日志切面
 * @Author AlanLiang
 * Date 2020/4/26 11:10
 * Version 1.0
 **/
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    @Autowired
    private OperationLogService operationLogService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();


    /**
     * 配置切入点
     */
    @Pointcut("@annotation(site.alanliang.geekblog.anntation.OperationLog)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        OperationLog log = new OperationLog("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        log.setStatus(Constant.SUCCESS);
        operationLogService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        OperationLog log = new OperationLog("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        log.setStatus(Constant.FAILURE);
        operationLogService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint) joinPoint, log);
    }

    public String getUsername() {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        HttpSession session = request.getSession();
        Object o = session.getAttribute("user");
        if (o != null) {
            User user = (User) o;
            return user.getUsername();
        }
        return "";
    }
}
