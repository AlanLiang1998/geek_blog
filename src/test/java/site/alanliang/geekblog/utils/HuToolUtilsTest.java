package site.alanliang.geekblog.utils;

import org.junit.jupiter.api.Test;
import site.alanliang.geekblog.query.ArticleQuery;

public class HuToolUtilsTest {
    @Test
    void test() {
        ArticleQuery articleQuery = new ArticleQuery();
        boolean b = ObjectUtil.ObjAllFieldsIsNull(articleQuery);
        System.out.println(b);
    }
}
