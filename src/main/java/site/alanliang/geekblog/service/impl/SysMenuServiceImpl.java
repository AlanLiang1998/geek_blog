package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alanliang.geekblog.domain.SysMenu;
import site.alanliang.geekblog.mapper.SysMenuMapper;
import site.alanliang.geekblog.service.SysMenuService;
import site.alanliang.geekblog.vo.init.InitInfoVo;


import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 22:15
 * Version 1.0
 **/
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public InitInfoVo menu() {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status", true);
        List<SysMenu> menuList = sysMenuMapper.selectList(wrapper);
        return InitInfoVo.init(menuList);

    }
}
