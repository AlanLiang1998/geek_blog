package site.alanliang.geekblog.utils;

import site.alanliang.geekblog.vo.init.MenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descriptin 菜单树工具
 * @Author AlanLiang
 * Date 2020/4/5 22:11
 * Version 1.0
 **/
public class MenuTreeUtil {
    public static List<MenuVo> toTree(List<MenuVo> treeList, Long pid) {
        List<MenuVo> retList = new ArrayList<MenuVo>();
        for (MenuVo parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }
    private static MenuVo findChildren(MenuVo parent, List<MenuVo> treeList) {
        for (MenuVo child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}

