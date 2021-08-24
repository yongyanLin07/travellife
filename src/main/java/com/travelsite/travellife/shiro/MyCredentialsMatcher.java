package com.travelsite.travellife.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.CodecSupport;

public class MyCredentialsMatcher extends SimpleCredentialsMatcher {
    //自定义密码验证匹配器
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;

        // 获取尝试登录用户明文密码
        String password = String.valueOf(token.getPassword());
        String salt = CodecSupport.toString(info.getCredentialsSalt().getBytes());
        //获取加密后密码
        String encrypt = ShiroUtil.encrypt(password, salt);
        //密码校验
        return equals(encrypt, info.getCredentials());
    }

}
