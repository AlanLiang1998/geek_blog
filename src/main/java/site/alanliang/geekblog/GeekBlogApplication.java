package site.alanliang.geekblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author AlanLiang
 */

@EnableAsync
@SpringBootApplication
public class GeekBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeekBlogApplication.class, args);
    }

}
