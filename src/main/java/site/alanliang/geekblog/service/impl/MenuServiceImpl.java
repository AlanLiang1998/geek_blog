package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.dao.MenuMapper;
import site.alanliang.geekblog.model.Role;
import site.alanliang.geekblog.service.MenuService;
import site.alanliang.geekblog.utils.MenuTreeUtil;
import site.alanliang.geekblog.vo.InitInfoVO;
import site.alanliang.geekblog.vo.MenuSelectVO;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Long countAll() {
        return Long.valueOf(menuMapper.selectCount(null));
    }

    @Override
    public InitInfoVO menu() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("status", true);
        List<Menu> menuList = menuMapper.selectList(wrapper);
        return InitInfoVO.init(menuList);

    }

    @Override
    public Menu getById(Long id) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "title", "href", "icon", "authority", "sort", "type", "status")
                .eq("id", id);
        return menuMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        menuMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Menu menu) {
        if (menu.getId() == null) {
            //保存
            //检查菜单标题是否唯一
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            wrapper.eq("title", menu.getTitle());
            if (!CollectionUtils.isEmpty(menuMapper.selectList(wrapper))) {
                throw new EntityExistException("菜单标题：" + menu.getTitle() + "已存在");
            }
            menuMapper.insert(menu);
        } else {
            //更新
            //检查菜单标题是否唯一
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            wrapper.eq("title", menu.getTitle());
            List<Menu> menus = menuMapper.selectList(wrapper);
            menus = menus.stream().filter(m -> !m.getId().equals(menu.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(menus)) {
                throw new EntityExistException("菜单标题：" + menu.getTitle() + "已存在");
            }
            menuMapper.updateById(menu);
        }
    }

    @Override
    public List<MenuSelectVO> listByTree() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "title");
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuSelectVO> treeList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuSelectVO menuSelectVO = new MenuSelectVO();
            menuSelectVO.setValue(menu.getId());
            menuSelectVO.setName(menu.getTitle());
            menuSelectVO.setPid(menu.getPid());
            treeList.add(menuSelectVO);
        }
        return MenuTreeUtil.toSelectTree(treeList, 0L);
    }

    @Override
    public List<Menu> listAll() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "title", "href", "authority", "icon", "sort", "type", "status", "create_time", "update_time");
        return menuMapper.selectList(wrapper);
    }
}
