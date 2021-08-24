package com.travelsite.travellife.aspect;

import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.utils.AesUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
//加解密切面，拦截@EncryptMethod和@DecryptMethod修饰的方法，并对方法中参数的特定属性（使用@CryptField注解的属性）进行加解密处理。
@Order(Ordered.HIGHEST_PRECEDENCE)
@Aspect
@Component
public class CryptFieldAspect {
    private static Logger logger = LoggerFactory.getLogger(CryptFieldAspect.class);
    private static String secretKey = "abcdefg";
    @Autowired
    private UserService userService;

    //切入点，匹配自定义注解的方法，确定需要代理的方法
    @Pointcut("@annotation(EncryptMethod)")
    public void encryptPointCut() {
    }
    @Pointcut("@annotation(DecryptMethod)")
    public void decryptPointCut() {
    }
    //加密/解密数据环绕处理反射机制，定义目标方法执行的时机，jointPoint为封装代理方法信息的对象
    @Around("encryptPointCut()")
    public Object aroundEncrypt(ProceedingJoinPoint joinPoint) throws Throwable {
        //传入目标方法的参数
        Object requestObj = joinPoint.getArgs()[0];
        handleEncrypt(requestObj); // 加密CryptField注解字段
        Object responseObj = joinPoint.proceed(); // 方法执行
        return responseObj;
    }
    @Around("decryptPointCut()")
    public Object aroundDecrypt(ProceedingJoinPoint joinPoint) throws Throwable {
        //Object requestObj = joinPoint.getArgs()[0];
        Object responseObj = joinPoint.proceed(); // 方法执行
        handleDecrypt(responseObj); // 解密CryptField注解字段
        return responseObj;

    }
    /**
     * 处理加密
     */
    public void handleEncrypt(Object requestObj) {
        if (Objects.isNull(requestObj)) {
            return;
        }
        //获取所有参数对象，反射机制
        Field[] fields = requestObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            //反射机制获取判断是否存在注解需加密字段
            boolean hasSecureField = field.isAnnotationPresent(CryptField.class);
            if (hasSecureField) {
                field.setAccessible(true);
                try {
                    //拿到该对象实例的域成员值
                    String plaintextValue = (String) field.get(requestObj);
                    String encryptValue = AesUtil.encrypt(plaintextValue, secretKey);
                    field.set(requestObj, encryptValue);
                } catch (Exception e) {

                }

            }
        }
    }
    /**
     * 处理解密
     */
    public Object handleDecrypt(Object responseObj)  {
        if (Objects.isNull(responseObj)) {
            return null;
        }
        //如果是List
        if (responseObj instanceof List) {
            List page = (List) responseObj;
            page.forEach(o -> {
                handleDecrypt(o);
            });
        }

        Field[] fields = responseObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean hasSecureField = field.isAnnotationPresent(CryptField.class);
            if (hasSecureField) {
                field.setAccessible(true);
                try {
                    String encryptValue = (String) field.get(responseObj);
                    String plaintextValue = AesUtil.decrypt(encryptValue, secretKey);
                    System.out.println(plaintextValue);
                    field.set(responseObj, plaintextValue);
                } catch (Exception e) {
                }
            }
        }
        return responseObj;
    }

}
