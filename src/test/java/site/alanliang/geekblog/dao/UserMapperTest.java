package site.alanliang.geekblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.model.User;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSave() {
        long number = 18600000001L;
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            User user = new User();
            String uuid = UUID.randomUUID().toString().substring(0, 5);
            user.setUsername(uuid);
            user.setNickname(uuid);
            user.setPassword(uuid);
            user.setAvatar("http://q8rsjstig.bkt.clouddn.com/note/default_avatar.jpg");
            user.setEmail(uuid + "@qq.com");
            user.setPhone(Long.toString(number));
            number += 1;
            user.setSex(random.nextBoolean());
            user.setStatus(1);
            user.setCreateTime(new Date());
            user.setUpdateTime(user.getCreateTime());
            userMapper.insert(user);
        }
    }
}