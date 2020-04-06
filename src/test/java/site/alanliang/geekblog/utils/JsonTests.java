package site.alanliang.geekblog.utils;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.vo.init.HomeInfo;
import site.alanliang.geekblog.vo.init.LogoInfo;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 10:45
 * Version 1.0
 **/
@SpringBootTest
public class JsonTests {
    @Test
    void testToJSONString(){
        HomeInfo homeInfo = new HomeInfo();
        homeInfo.setTitle("首页");
        homeInfo.setHref("/static/layuimini/page/welcome-3.html");
        LogoInfo logoInfo = new LogoInfo();
        logoInfo.setTitle("Geek Blog");
        logoInfo.setImage("/static/layuimini/images/logo.png");
        System.out.println(JSON.toJSONString(homeInfo));
        System.out.println(JSON.toJSONString(logoInfo));
    }
}
