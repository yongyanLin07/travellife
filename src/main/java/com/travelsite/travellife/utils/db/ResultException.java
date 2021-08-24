package com.travelsite.travellife.utils.db;

import lombok.Getter;

/**
 * 自定义异常对象

 */
@Getter
public class ResultException extends RuntimeException {

    private Integer code;

    /**
     * 统一异常处理
     * @param resultEnum 状态枚举
     */
    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
