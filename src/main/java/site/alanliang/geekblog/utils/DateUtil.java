package site.alanliang.geekblog.utils;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 12:01
 * Version 1.0
 **/
public class DateUtil {
    public static String formatDate(Integer year, Integer month, Integer day) {
        if (day != null) {
            return String.format("%4d-%02d-%02d", year, month, day);
        } else {
            return String.format("%4d-%02d", year, month);
        }
    }
}
