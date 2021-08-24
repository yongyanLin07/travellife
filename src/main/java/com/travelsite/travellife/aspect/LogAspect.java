package com.travelsite.travellife.aspect;

import com.travelsite.travellife.po.LogData;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

//日志记录切面
@Aspect
@Component//交由Spring管理
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Autowired
    private LogService logService;
    //匹配自定义注解的方法，确定需要代理的方法
    @Pointcut("@annotation(LogMethod)")
    public void pointcut() {}

    //代理+闭包，执行前后操作,JointPoint封装代理方法,拦截jointPoint
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result =null;
        try {
            //执行目标方法
            result = point.proceed();
            saveLog(point);
        } catch (Throwable e) {

        }
        return result;
    }


    private void saveLog(ProceedingJoinPoint point) {
        //获取封装的署名对象
        MethodSignature signature = (MethodSignature)point.getSignature();
        //得到方法
        Method method = signature.getMethod();
        //新建日志对象
        LogData sys_log = new LogData();
        //返回指定注释类型
        LogMethod userAction = method.getAnnotation(LogMethod.class);
        if (userAction != null) {
            // 注解上value的定义
            sys_log.setUser_action(userAction.value());
        }
        // 被代理对象所在的类名
        String className = point.getTarget().getClass().getName();
        // 方法名
        String methodName = signature.getName();
        // 方法参数值
        String args = Arrays.toString(point.getArgs());
        //当前操作用户
        USER user = (USER) SecurityUtils.getSubject().getSession().getAttribute("user");
        if(user != null){
            sys_log.setUser(user);
            sys_log.setTime(new java.sql.Timestamp(new Date().getTime()));
            logger.info("当前登陆人：{},类名:{},方法名:{},参数：{}",user.getId(), className, methodName, args);
            logService.save(sys_log);
        }
    }
}
