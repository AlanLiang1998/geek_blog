package site.alanliang.geekblog.utils;

import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.model.User;

public class UserInfoUtil {
    public static String getUsername() {
        Object o = RequestHolder.getHttpServletRequest().getSession().getAttribute(Constant.USER);
        String username = "";
        if (o != null) {
            User user = (User) o;
            username = user.getUsername();
        }
        return username;
    }
}
