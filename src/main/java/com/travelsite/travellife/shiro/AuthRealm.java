package com.travelsite.travellife.shiro;

import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.utils.db.AdminConst;
import com.travelsite.travellife.utils.db.StatusEnum;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import java.util.Set;


public class AuthRealm extends AuthorizingRealm {

    //shiro中使用
    @Lazy
    @Autowired
    private UserService userService;

    /**
     * 认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取数据库中的用户名密码
        USER user = userService.getUserByName(token.getUsername());

        // 判断用户名是否存在
        if (user == null) {
            throw new UnknownAccountException();
        }
        // 字符串转为盐值信息
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        //返回AuthenticationInfo (principal,密码,盐，realm名)
        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }
    /**
     * 授权逻辑，传入用户认证凭证信息
     */
    //@RequiresPermission("")在方法注解访问调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前用户
        USER user = (USER) principal.getPrimaryPrincipal();
        // 管理员拥有所有权限
        if (user.getId().equals(AdminConst.ADMIN_ID)) {
            info.addRole(AdminConst.ADMIN_ROLE_NAME);
            info.addStringPermission("*:*:*");
            return info;
        }

        // 赋予当前其他用户角色和资源授权
        //获取当前用户角色
        Set<Role> roles = ShiroUtil.getSubjectRoles();
        roles.forEach(role -> {
            info.addRole(role.getName());
            role.getMenus().forEach(menu -> {
                String perms = menu.getPerms();
                if (menu.getStatus().equals(StatusEnum.OK.getCode())
                        && !StringUtils.isEmpty(perms) && !perms.contains("*")) {
                    info.addStringPermission(perms);
                }
            });
        });

        return info;
    }


}
