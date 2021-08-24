package com.travelsite.travellife.shiro;


import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.UserService;
import freemarker.template.utility.DateUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.cache.Cache;


public class RetryLimitHashedCredentialsMatcher extends MyCredentialsMatcher {


    private final int retryLimit = 3;
    private final int retryLimitTime = 20;//分钟



    @Lazy
    @Autowired
    private UserService userService;

    private Cache passwordRetryCache;
//<String, AtomicInteger>
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //获取用户名
        String username = (String)token.getPrincipal();
        //获取用户登录次数
        AtomicInteger retryCount = passwordRetryCache.get(username,AtomicInteger.class);
        if (retryCount == null) {
            //如果用户没有登陆过,登陆次数加1 并放入缓存
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        USER user = userService.getUserByName(username);
        if (retryCount.incrementAndGet() > retryLimit) {
            //如果用户登陆失败次数大于限定次数 抛出锁定用户异常  并修改数据库字段
            if (user != null && 0==(user.getStatus())) {
                // 0  就是正常状态 1 锁定状态
                //修改数据库的状态字段为锁定
                user.setStatus(1);
                //设置时间
                //retryLimitTime
                long ms = retryLimitTime * 60 * 1000;
                long time = new Date().getTime() + ms;

                Date date = new Date(time);
                user.setRetryTime(date);
                user =userService.updateUser(user, user.getId());
                System.out.println("超出");
                System.out.println(user.getRetryTime());
            }
            //已经被封
            if (user != null && 1== (user.getStatus())) {
                Date retryTime = user.getRetryTime();
                //重试时间小于 当前时间解封
                if (retryTime.compareTo(new Date()) == -1) {
                    retryCount.set(0);
                    user.setStatus(0);
                    userService.updateUser(user, user.getId());
                } else {
                    System.out.println("锁定用户" + user.getName());
                    //抛出用户锁定异常
                    String msg = "锁定用户" + user.getName() + ";" + user.getRetryTime() + "重新尝试";
                    throw new LockedAccountException(msg);
                }
            }
        }
        //判断用户账号和密码是否正确
        //调用父类MyCredentialsMatcher进行密码校验
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //如果正确,从缓存中将用户登录计数 清除
            passwordRetryCache.evictIfPresent(username);
        }
        return matches;
    }

    /**
     * 根据用户名 解锁用户
     */
    public void unlockAccount(String username){
        USER user = userService.getUserByName(username);
        if (user != null){
            //修改数据库的状态字段为锁定
            user.setStatus(0);
            userService.updateUser(user,user.getId());
            passwordRetryCache.evictIfPresent(username);
        }
    }

}