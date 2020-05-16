package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.dao.UserMapper;
import site.alanliang.geekblog.model.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void listTableByPage() {
        Page<User> page = new Page<>(1, 10);
        Page<User> userPage = userMapper.listTableByPage(page, null);
        for (User user : userPage.getRecords()) {
            System.out.println(user);
        }
    }
}