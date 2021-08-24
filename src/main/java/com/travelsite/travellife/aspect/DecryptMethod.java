package com.travelsite.travellife.aspect;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

//解密方法注解
@Documented
@Target({ElementType.METHOD})
//通过反射得到数据值
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface DecryptMethod {
}
