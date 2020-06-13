package site.alanliang.geekblog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Descriptin 通用响应实体
 * @Author AlanLiang
 * Date 2020/4/6 17:33
 * Version 1.0
 **/
@ApiModel("统一返回结果")
@Getter
@Setter
public class JsonResult implements Serializable {
    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("响应消息")
    private String msg;

    @ApiModelProperty("响应数据")
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
