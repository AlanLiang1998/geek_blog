package site.alanliang.geekblog.aspect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.alanliang.geekblog.anntation.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Descriptin 日志切面
 * @Author AlanLiang
 * Date 2020/4/6 15:14
 * Version 1.0
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(site.alanliang.geekblog.anntation.Log)")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取请求方法
        String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        //计算时长
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        //获取注解描述
        String description = signature.getMethod().getAnnotation(Log.class).value();

        RequestLog requestLog = new RequestLog();
        requestLog.setUrl(request.getRequestURL().toString());
        requestLog.setIp(request.getRemoteAddr());
        requestLog.setClassMethod(classMethod);
        requestLog.setArgs(joinPoint.getArgs());
        requestLog.setTime(time);
        requestLog.setDescription(description);
        log.info("Request ------ {}", requestLog);
        return result;
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        log.info("Return ------ {}", result);
    }

    @Data
    private static class RequestLog {
        //请求url
        private String url;
        //请求ip
        private String ip;
        //请求方法
        private String classMethod;
        //请求方法参数
        private Object[] args;
        //执行时长（ms）
        private long time;
        //方法描述
        private String description;

        @Override
        public String toString() {
            return "RequestLog {" +
                    "url = " + url +
                    ", ip = " + ip +
                    ", classMethod = " + classMethod +
                    ", args = " + Arrays.toString(args) +
                    ", time = " + time + "ms" +
                    ", description = " + description +
                    "}";
        }
    }
}
