package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alanliang.geekblog.dao.RoleMapper;
import site.alanliang.geekblog.model.Role;
import site.alanliang.geekblog.service.RoleService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 10:38
 * Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> listAll() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id", "role_name");
        return roleMapper.selectList(wrapper);
    }
}
