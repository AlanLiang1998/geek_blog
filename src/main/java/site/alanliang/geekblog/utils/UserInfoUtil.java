package site.alanliang.geekblog.utils;

import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.model.User;

public class UserInfoUtil {
    public static String getUsername() {
        Object o = RequestHolder.getHttpServletRequest().getSession().getAttribute(Constant.USER);
        String username = "";
        if (o != null) {
            User user = (User) o;
            username = user.getUsername();
        } else {
            throw new BadRequestException("用户未登录");
        }
        return username;
    }

    public static Long getId() {
        Object o = RequestHolder.getHttpServletRequest().getSession().getAttribute(Constant.USER);
        Long id = null;
        if (o != null) {
            User user = (User) o;
            id = user.getId();
        }
        return id;
    }
}
