package site.alanliang.geekblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.alanliang.geekblog.interceptor.LoginInterceptor;
import site.alanliang.geekblog.interceptor.LoginPageInterceptor;

/**
 * @Descriptin WebMvc配置
 * @Author AlanLiang
 * Date 2020/4/6 9:37
 * Version 1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 添加静态资源文件，外部可以直接访问地址
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/401");

        registry.addInterceptor(new LoginPageInterceptor())
                .addPathPatterns("/admin");
    }*/
}
