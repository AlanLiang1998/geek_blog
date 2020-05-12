package site.alanliang.geekblog.utils;

import site.alanliang.geekblog.vo.MenuSelectVO;
import site.alanliang.geekblog.vo.MenuVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descriptin 菜单树工具
 * @Author AlanLiang
 * Date 2020/4/5 22:11
 * Version 1.0
 **/
public class MenuTreeUtil {
    public static List<MenuVO> toTree(List<MenuVO> treeList, Long pid) {
        List<MenuVO> retList = new ArrayList<MenuVO>();
        for (MenuVO parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChild(parent, treeList));
            }
        }
        return retList;
    }

    private static MenuVO findChild(MenuVO parent, List<MenuVO> treeList) {
        for (MenuVO child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChild(child, treeList));
            }
        }
        return parent;
    }

    public static List<MenuSelectVO> toSelectTree(List<MenuSelectVO> treeList, Long pid) {
        List<MenuSelectVO> retList = new ArrayList<MenuSelectVO>();
        for (MenuSelectVO parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    private static MenuSelectVO findChildren(MenuSelectVO parent, List<MenuSelectVO> treeList) {
        for (MenuSelectVO children : treeList) {
            if (parent.getValue().equals(children.getPid())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildren(children, treeList));
            }
        }
        return parent;
    }

}

