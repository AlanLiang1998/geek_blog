package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.domain.SysUser;
import site.alanliang.geekblog.mapper.SysUserMapper;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 20:58
 * Version 1.0
 **/
@SpringBootTest
public class SysUserServiceImplTests {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    void testListByPage(){
        Page<SysUser> page = new Page<>(1, 5);
        Page<SysUser> pageInfo = sysUserMapper.selectPage(page, null);
        System.out.println(pageInfo.getRecords());
    }
}
