package site.alanliang.geekblog.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

    public JsonResult() {
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult build(Integer code, String message) {
        return new JsonResult(code, message);
    }

    public static JsonResult build(Integer code, String message, Object data) {
        return new JsonResult(code, message, data);
    }

    public static JsonResult ok() {
        JsonResult result = new JsonResult();
        result.setCode(200);
        result.setMsg("OK");
        return result;
    }

    public static JsonResult ok(Object data) {
        JsonResult result = new JsonResult();
        result.setCode(200);
        result.setMsg("OK");
        result.setData(data);
        return result;
    }

    public static JsonResult fail(String msg) {
        JsonResult result = new JsonResult();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}
