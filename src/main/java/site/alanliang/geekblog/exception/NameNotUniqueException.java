package site.alanliang.geekblog.exception;

/**
 * @Descriptin 名称不唯一异常
 * @Author AlanLiang
 * Date 2020/4/9 13:37
 * Version 1.0
 **/
public class NameNotUniqueException extends RuntimeException {
    public NameNotUniqueException(String message) {
        super(message);
    }
}
