package site.alanliang.geekblog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class RedisUtilsTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Test
    void get() {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        redisUtils.set("name", "Alan");
    }

    @Test
    void scan() {
        List<String> list = redisUtils.scan("article*");
        String[] keys = new String[list.size()];
        list.toArray(keys);
        System.out.println(keys.length);
    }
}