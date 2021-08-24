package com.travelsite.travellife.service;

import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.utils.db.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


public interface RoleService {

    /**
     * 获取用户角色列表
     * @param id 用户ID
     * @return 角色列表
     */
    //    @Transactional
    Set<Role> getUserOkRoleList(Long id);

    /**
     * 判断指定的用户是否存在角色
     * @param id 用户ID
     * @return 是否存在角色
     */
    Boolean existsUserOk(Long id);

    /**
     * 根据角色ID查询角色数据
     * @param id 角色ID
     * @return 角色信息
     */
    //    @Transactional
    Role getById(Long id);

    /**
     * 获取角色列表数据
     * @param sort 排序对象
     * @return 角色列表
     */
    List<Role> getListBySortOk(Sort sort);

    /**
     * 角色标识是否重复
     * @param role 角色实体类
     * @return 标识是否重复
     */
    boolean repeatByName(Role role);

    /**
     * 保存角色
     * @param role 角色实体类
     * @return 角色信息
     */
    Role save(Role role);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     * @param statusEnum 数据状态
     * @param idList 数据ID列表
     * @return 操作结果
     */
    //    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    Page<Role> listRole(Pageable pageable);

    void delete(Long id);
}
