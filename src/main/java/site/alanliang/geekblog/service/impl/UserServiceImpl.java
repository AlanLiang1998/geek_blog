package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alanliang.geekblog.entity.SysUser;
import site.alanliang.geekblog.dao.UserMapper;
import site.alanliang.geekblog.service.UserService;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 20:52
 * Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Page<SysUser> listByPage(int current, int size) {
        Page<SysUser> page = new Page<>(current, size);
        return userMapper.selectPage(page, null);
    }

    @Override
    public SysUser checkUser(String username, String password) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username")
                .eq("username", username)
                .eq("password", password);
        return userMapper.selectOne(wrapper);
    }
}
