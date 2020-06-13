package site.alanliang.geekblog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Descriptin 数据表格响应实体
 * @Author AlanLiang
 * Date 2020/4/6 17:33
 * Version 1.0
 **/
@ApiModel("数据表格统一返回结果")
@Data
@EqualsAndHashCode(callSuper = true)
public class TableResult extends JsonResult implements Serializable {

    @ApiModelProperty("记录总数")
    private Long count;

    public static TableResult tableOk(Object data, Long count) {
        TableResult result = new TableResult();
        result.setCode(0);
        result.setMsg("ok");
        result.setCount(count);
        result.setData(data);
        return result;
    }
}
