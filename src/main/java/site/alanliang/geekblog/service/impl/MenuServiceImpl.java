package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.dao.MenuMapper;
import site.alanliang.geekblog.service.MenuService;
import site.alanliang.geekblog.vo.InitInfoVO;


import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 22:15
 * Version 1.0
 **/
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public InitInfoVO menu() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("status", true);
        List<Menu> menuList = menuMapper.selectList(wrapper);
        return InitInfoVO.init(menuList);

    }
}
