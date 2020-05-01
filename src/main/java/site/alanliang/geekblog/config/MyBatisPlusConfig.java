package site.alanliang.geekblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Descriptin MyBatisPlus配置
 * @Author AlanLiang
 * Date 2020/4/6 21:08
 * Version 1.0
 **/
@EnableTransactionManagement
@Configuration
@MapperScan("site.alanliang.geekblog.dao")
public class MyBatisPlusConfig {
    /**
     * 开启分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }


}
