package site.alanliang.geekblog.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.domain.SysUser;

import java.util.Date;
import java.util.UUID;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 21:06
 * Version 1.0
 **/
@SpringBootTest
@Slf4j
public class SysUserMapperTests {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    void testSelectById() {
        SysUser sysUser = sysUserMapper.selectById(1L);
        log.info("sysUser : {}", sysUser);
    }

    @Test
    void testInsert() {
        for (long i = 0; i < 100; i++) {
            SysUser sysUser = new SysUser();
            String uuid = UUID.randomUUID().toString().substring(0, 5);
            //sysUser.setId(i);
            sysUser.setUsername(uuid);
            sysUser.setPassword(uuid);
            sysUser.setEmail(uuid + "@163.com");
            sysUser.setNickname(uuid);
            sysUser.setCreateTime(new Date());
            sysUser.setUpdateTime(sysUser.getCreateTime());
            sysUserMapper.insert(sysUser);
        }
    }


    @Test
    void testDelete() {
        sysUserMapper.delete(null);
    }
}
