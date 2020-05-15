package site.alanliang.geekblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.alanliang.geekblog.security.MyAccessDeniedHandler;
import site.alanliang.geekblog.security.MyAuthenticationFailureHandler;
import site.alanliang.geekblog.security.MyAuthenticationSuccessHandler;
import site.alanliang.geekblog.security.ValidateCodeFilter;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/14 17:02
 * Version 1.0
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    private final MyAccessDeniedHandler myAccessDeniedHandler;

    private final UserDetailsService userDetailsService;

    private final ValidateCodeFilter validateCodeFilter;

    @Autowired
    public WebSecurityConfig(MyAuthenticationSuccessHandler myAuthenticationSuccessHandler,
                             MyAuthenticationFailureHandler myAuthenticationFailureHandler,
                             MyAccessDeniedHandler myAccessDeniedHandler,
                             @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService,
                             ValidateCodeFilter validateCodeFilter) {
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.userDetailsService = userDetailsService;
        this.validateCodeFilter = validateCodeFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //放行静态资源和登录请求
                .antMatchers("/static/**", "/admin/login", "/admin/login.html", "/admin/captcha").permitAll()
                //其余请求在认证后访问
                .antMatchers("/admin/**").authenticated();

        //添加图形验证码
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //允许表单登录
                .formLogin()
                //自定义登录页面
                .loginPage("/admin/login.html")
                //自定义登录表单提交路径
                .loginProcessingUrl("/admin/login")
                //认证成功处理
                .successHandler(myAuthenticationSuccessHandler)
                //认证失败处理
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                //记住我
                .rememberMe();

        http.logout().permitAll()
                .logoutUrl("/admin/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/admin/login.html");

        //处理权限异常
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        //禁用拦截除GET方式以外的请求
        http.csrf().disable();
        //解决X-Frame-Options DENY问题
        http.headers().frameOptions().sameOrigin();
    }
}
