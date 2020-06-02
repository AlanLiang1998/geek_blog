package site.alanliang.geekblog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisUtilsTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void get() {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        redisUtils.set("name", "Alan");
    }
}