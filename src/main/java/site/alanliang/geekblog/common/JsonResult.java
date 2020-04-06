package site.alanliang.geekblog.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Descriptin 通用响应实体
 * @Author AlanLiang
 * Date 2020/4/6 17:33
 * Version 1.0
 **/
@Getter
@Setter
public class JsonResult implements Serializable {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应数据
     */
    private Object data;

    public JsonResult(){}

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static  JsonResult build(Integer code, String message) {
        return new JsonResult(code, message);
    }

    public static JsonResult build(Integer code, String message, Object data) {
        return new JsonResult(code, message, data);
    }

    public static  JsonResult ok() {
        JsonResult result = new JsonResult();
        result.setCode(200);
        result.setMessage("OK");
        return result;
    }

    public static JsonResult ok(Object data) {
        JsonResult result = new JsonResult();
        result.setCode(200);
        result.setMessage("OK");
        result.setData(data);
        return result;
    }
}
