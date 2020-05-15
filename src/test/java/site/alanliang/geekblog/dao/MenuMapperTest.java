package site.alanliang.geekblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.model.Menu;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuMapperTest {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    void listPermissionByUserId() {
        List<Menu> menus = menuMapper.listPermissionByUserId(206L);
        System.out.println(menus.size());
        for (Menu menu : menus) {
            System.out.println(menu.getAuthority());
        }
    }
}