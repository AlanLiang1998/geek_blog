package site.alanliang.geekblog.service;

import site.alanliang.geekblog.vo.init.InitInfoVo;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 22:15
 * Version 1.0
 **/
public interface SysMenuService {
    /**
     * 获取菜单树
     * @return
     */
    InitInfoVo menu();
}
