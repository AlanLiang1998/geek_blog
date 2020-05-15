package site.alanliang.geekblog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/2 17:01
 * Version 1.0
 **/
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.pathEquals("/admin/login", httpServletRequest.getRequestURI())) {
            String verCode = httpServletRequest.getParameter("verCode");
            if (!CaptchaUtil.ver(verCode, httpServletRequest)) {
                CaptchaUtil.clear(httpServletRequest);  // 清除session中的验证码
                Map<String, Object> map = new HashMap<>();
                map.put("message", "验证码错误");
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().write(objectMapper.writeValueAsString(map));
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
