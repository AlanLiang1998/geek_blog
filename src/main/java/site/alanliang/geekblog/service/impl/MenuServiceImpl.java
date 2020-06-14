package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.MenuMapper;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.service.MenuService;
import site.alanliang.geekblog.utils.MenuTreeUtil;
import site.alanliang.geekblog.vo.InitInfoVO;
import site.alanliang.geekblog.vo.MenuCheckboxVO;
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
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @Cacheable
    public Long countAll() {
        return Long.valueOf(menuMapper.selectCount(null));
    }

    @Override
    @Cacheable
    public InitInfoVO menu(Long userId) {
        List<Menu> menuList = menuMapper.listMenuByUserId(userId);
        return InitInfoVO.init(menuList);
    }

    @Override
    @Cacheable
    public Menu getById(Long id) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select(Menu.Table.ID, Menu.Table.PID, Menu.Table.TITLE, Menu.Table.HREF, Menu.Table.ICON, Menu.Table.AUTHORITY, Menu.Table.SORT, Menu.Table.TYPE, Menu.Table.STATUS)
                .eq(Menu.Table.ID, id);
        return menuMapper.selectOne(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        menuMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Menu menu) {
        if (menu.getId() == null) {
            //保存
            //检查菜单标题是否唯一
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            wrapper.eq(Menu.Table.TITLE, menu.getTitle());
            if (!CollectionUtils.isEmpty(menuMapper.selectList(wrapper))) {
                throw new EntityExistException("菜单标题：" + menu.getTitle() + "已存在");
            }
            menuMapper.insert(menu);
        } else {
            //更新
            //检查菜单标题是否唯一
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            wrapper.eq(Menu.Table.TITLE, menu.getTitle());
            List<Menu> menus = menuMapper.selectList(wrapper);
            menus = menus.stream().filter(m -> !m.getId().equals(menu.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(menus)) {
                throw new EntityExistException("菜单标题：" + menu.getTitle() + "已存在");
            }
            menuMapper.updateById(menu);
        }
    }

    @Override
    @Cacheable
    public List<MenuCheckboxVO> listByCheckboxTree() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select(Menu.Table.ID, Menu.Table.PID, Menu.Table.TITLE);
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuCheckboxVO> treeList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuCheckboxVO menuCheckboxVO = new MenuCheckboxVO();
            menuCheckboxVO.setId(menu.getId());
            menuCheckboxVO.setParentId(menu.getPid());
            menuCheckboxVO.setTitle(menu.getTitle());
            menuCheckboxVO.setCheckArr(Constant.MENU_TREE_NOT_SELECTED);
            treeList.add(menuCheckboxVO);
        }
        return MenuTreeUtil.toCheckBoxTree(treeList, Constant.MENU_TREE_START);
    }

    @Override
    @Cacheable
    public List<MenuSelectVO> listBySelectTree() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select(Menu.Table.ID, Menu.Table.PID, Menu.Table.TITLE);
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuSelectVO> treeList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuSelectVO menuSelectVO = new MenuSelectVO();
            menuSelectVO.setValue(menu.getId());
            menuSelectVO.setName(menu.getTitle());
            menuSelectVO.setPid(menu.getPid());
            treeList.add(menuSelectVO);
        }
        return MenuTreeUtil.toSelectTree(treeList, Constant.MENU_TREE_START);
    }

    @Override
    @Cacheable
    public List<Menu> listAll() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select(Menu.Table.ID, Menu.Table.PID, Menu.Table.TITLE, Menu.Table.HREF, Menu.Table.AUTHORITY, Menu.Table.ICON, Menu.Table.SORT, Menu.Table.TYPE, Menu.Table.STATUS, Menu.Table.CREATE_TIME, Menu.Table.UPDATE_TIME);
        return menuMapper.selectList(wrapper);
    }
}
