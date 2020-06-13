package site.alanliang.geekblog.exception;

public class StatusExpiredException extends RuntimeException {
    public StatusExpiredException(String message) {
        super(message);
    }
}
