package site.alanliang.geekblog.service;

import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.vo.InitInfoVO;
import site.alanliang.geekblog.vo.MenuSelectVO;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 22:15
 * Version 1.0
 **/
public interface MenuService {
    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    InitInfoVO menu();

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    List<Menu> listAll();

    /**
     * 查询菜单记录数目
     *
     * @return 菜单记录数目
     */
    Long countAll();

    /**
     * 查询菜单树
     *
     * @return 菜单树
     */
    List<MenuSelectVO> listByTree();

    /**
     * 保存或者更新菜单
     *
     * @param menu 菜单
     */
    void saveOfUpdate(Menu menu);

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单
     */
    Menu getById(Long id);

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     */
    void removeById(Long id);
}
