package site.alanliang.geekblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author AlanLiang
 */

@MapperScan("site.alanliang.geekblog.mapper")
@SpringBootApplication
public class GeekBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeekBlogApplication.class, args);
    }

}
