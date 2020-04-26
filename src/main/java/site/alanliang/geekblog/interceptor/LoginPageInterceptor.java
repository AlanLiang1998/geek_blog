package site.alanliang.geekblog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import site.alanliang.geekblog.anntation.Log;
import site.alanliang.geekblog.utils.RequestHolder;
import site.alanliang.geekblog.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/2/11 12:11
 * Version 1.0
 **/
@Slf4j
public class LoginPageInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = StringUtils.getIp(request);
        log.info("{}---请求登录页", ip);
        return true;
    }
}
