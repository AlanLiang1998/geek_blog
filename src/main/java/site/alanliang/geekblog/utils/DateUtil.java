package site.alanliang.geekblog.utils;

import java.util.Date;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/22 12:01
 * Version 1.0
 **/
public class DateUtil {
    public static String formatDate(int year, int month, int day) {
        return String.format("%4d-%02d-%02d", year, month, day);
    }
}
