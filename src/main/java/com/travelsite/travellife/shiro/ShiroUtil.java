package com.travelsite.travellife.shiro;

import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;

import java.util.Set;

/**
 * Shiro工具类
 *
 */
public class ShiroUtil {

    /**
     * 加密算法
     */
    public final static String HASH_ALGORITHM_NAME = EncryptUtil.HASH_ALGORITHM_NAME;

    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = EncryptUtil.HASH_ITERATIONS;



    public static String encrypt(String password, String salt) {
        return EncryptUtil.encrypt(password, salt, HASH_ALGORITHM_NAME, HASH_ITERATIONS);
    }

    /**
     * 获取当前用户对象
     */
    public static USER getSubject() {
        try {
            USER user = (USER) SecurityUtils.getSubject().getPrincipal();
            return user;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 获取当前用户角色列表
     */
    public static Set<Role> getSubjectRoles() {
        USER user = (USER) SecurityUtils.getSubject().getPrincipal();

        // 如果用户为空，则返回空列表
        if (user == null) {
            user = new USER();
        }

        // 判断角色列表是否已缓存
        if (!Hibernate.isInitialized(user.getRoles())) {
            try {
                Hibernate.initialize(user.getRoles());
            } catch (LazyInitializationException e) {
                // 延迟加载超时，重新查询角色列表数据
                RoleService roleService = SpringContextUtil.getBean(RoleService.class);
                user.setRoles(roleService.getUserOkRoleList(user.getId()));
            }
        }

        return user.getRoles();
    }

}
