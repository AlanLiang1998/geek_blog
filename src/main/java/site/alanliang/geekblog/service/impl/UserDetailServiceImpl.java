package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.alanliang.geekblog.dao.MenuMapper;
import site.alanliang.geekblog.dao.UserMapper;
import site.alanliang.geekblog.dto.LoginUser;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/3/31 21:07
 * Version 1.0
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        List<User> users = userMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(users)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (users.size() != 1) {
            return null;
        }
        User user = users.get(0);
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);
        //查询用户的权限
        List<Menu> permissions = menuMapper.listPermissionByUserId(loginUser.getId());
        //过滤空权限
        permissions = permissions.stream().filter(p -> (!StringUtils.isEmpty(p.getAuthority()))).collect(Collectors.toList());
        loginUser.setPermissions(permissions);
        return loginUser;
    }
}
