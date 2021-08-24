package com.travelsite.travellife.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应数据返回给前端最外层对象
 */
@ApiModel("响应结果")
public class ResultVo<T> {

    /** 状态码 */
    @ApiModelProperty(notes = "状态码（200成功、400错误）")
    private Integer code;

    /** 提示信息 */
    @ApiModelProperty(notes = "提示信息")
    private String msg;

    /** 响应数据 ,定义为通用泛型*/
    @ApiModelProperty(notes = "响应数据")
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
