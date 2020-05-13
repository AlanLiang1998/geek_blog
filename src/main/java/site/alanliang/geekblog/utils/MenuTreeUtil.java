package site.alanliang.geekblog.utils;

import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.vo.MenuCheckboxVO;
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
        List<MenuVO> retList = new ArrayList<>();
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
        MenuSelectVO root = new MenuSelectVO();
        root.setName(Constant.MENU_TREE_ROOT_NAME);
        root.setValue(Constant.MENU_TREE_ROOT);
        root.setPid(pid);
        treeList.add(root);
        List<MenuSelectVO> retList = new ArrayList<>();
        for (MenuSelectVO parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChildSelect(parent, treeList));
            }
        }
        return retList;
    }

    private static MenuSelectVO findChildSelect(MenuSelectVO parent, List<MenuSelectVO> treeList) {
        for (MenuSelectVO children : treeList) {
            if (parent.getValue().equals(children.getPid())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildSelect(children, treeList));
            }
        }
        return parent;
    }

    public static List<MenuCheckboxVO> toCheckBoxTree(List<MenuCheckboxVO> treeList, Long pid) {
        MenuCheckboxVO root = new MenuCheckboxVO();
        root.setTitle(Constant.MENU_TREE_ROOT_NAME);
        root.setId(Constant.MENU_TREE_ROOT);
        root.setParentId(pid);
        root.setCheckArr(Constant.MENU_TREE_NOT_SELECTED);
        treeList.add(root);
        List<MenuCheckboxVO> retList = new ArrayList<>();
        for (MenuCheckboxVO parent : treeList) {
            if (pid.equals(parent.getParentId())) {
                retList.add(findChildCheckBox(parent, treeList));
            }
        }
        return retList;
    }

    private static MenuCheckboxVO findChildCheckBox(MenuCheckboxVO parent, List<MenuCheckboxVO> treeList) {
        for (MenuCheckboxVO children : treeList) {
            if (parent.getId().equals(children.getParentId())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildCheckBox(children, treeList));
            }
        }
        return parent;
    }
}

