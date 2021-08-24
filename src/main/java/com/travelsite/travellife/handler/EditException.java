package com.travelsite.travellife.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//自定义异常处理
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EditException extends RuntimeException {
    //添加想要自定义的异常
}
