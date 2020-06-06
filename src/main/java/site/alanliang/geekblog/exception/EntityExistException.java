package site.alanliang.geekblog.exception;

/**
 * @Descriptin 实体存在异常
 * @Author AlanLiang
 * Date 2020/4/9 13:37
 * Version 1.0
 **/
public class EntityExistException extends RuntimeException {
    public EntityExistException(String message) {
        super(message);
    }

    public EntityExistException(String entity, String field, String val) {
        super(EntityExistException.generateMessage(entity, field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return entity + "[" + field + "]: " + val + " 已存在";
    }
}
