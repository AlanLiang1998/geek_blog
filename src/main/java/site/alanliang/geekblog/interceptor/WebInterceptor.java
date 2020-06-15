package site.alanliang.geekblog.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import site.alanliang.geekblog.common.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/2/11 12:11
 * Version 1.0
 **/
@Component
public class WebInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constant.USER) == null) {
            response.sendRedirect("/admin/login.html");
            return false;
        } else {
            return true;
        }
    }
}
