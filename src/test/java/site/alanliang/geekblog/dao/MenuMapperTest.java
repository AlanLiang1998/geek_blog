package site.alanliang.geekblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.model.RoleMenu;

import java.util.List;

@SpringBootTest
class MenuMapperTest {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Test
    void listPermissionByUserId() {
        List<Menu> menus = menuMapper.listPermissionByUserId(206L);
        System.out.println(menus.size());
        for (Menu menu : menus) {
            System.out.println(menu.getAuthority());
        }
    }

    @Test
    void test(){
        for(long i = 1;i<=20;i++){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(1L);
            roleMenu.setMenuId(i);
            roleMenuMapper.insert(roleMenu);
        }
    }
}