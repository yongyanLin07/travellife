package com.travelsite.travellife.service;

import com.travelsite.travellife.dao.RoleRepository;
import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.utils.db.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 获取用户角色列表
     */
    @Override
    @Transactional
    public Set<Role> getUserOkRoleList(Long id) {
        Byte status = StatusEnum.OK.getCode();
        return roleRepository.findByUsers_IdAndStatus(id, status);
    }

    /**
     * 判断指定的用户是否存在角色
     */
    @Override
    public Boolean existsUserOk(Long id) {
        Byte status = StatusEnum.OK.getCode();
        return roleRepository.existsByUsers_IdAndStatus(id, status);
    }

    /**
     * 根据角色ID查询角色数据
     */
    @Override
    @Transactional
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }



    /**
     * 获取角色列表数据
     * @param sort 排序对象
     */
    @Override
    public List<Role> getListBySortOk(Sort sort) {
        return roleRepository.findAllByStatus(sort, StatusEnum.OK.getCode());
    }

    /**
     * 角色标识是否重复
     * @param role 角色实体类
     */
    @Override
    public boolean repeatByName(Role role) {
        Long id = role.getId() != null ? role.getId() : Long.MIN_VALUE;
        return roleRepository.findByNameAndIdNot(role.getName(), id) != null;
    }


    @Override
    @Transactional
    public Role save(Role role){
        return roleRepository.save(role);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> ids){
        // 删除角色时取消与角色和菜单的关联
        if(statusEnum == StatusEnum.DELETE){
            roleRepository.cancelUserJoin(ids);
            roleRepository.cancelMenuJoin(ids);
        }
        return roleRepository.updateStatus(statusEnum.getCode(), ids) > 0;

    }

    @Override
    public Page<Role> listRole(Pageable pageable) {
        Page<Role> all = roleRepository.findAll(pageable);
        return all;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        roleRepository.cancelMenuJoin(ids);
        roleRepository.cancelUserJoin(ids);
        Role role = roleRepository.findById(id).get();
        roleRepository.delete(role);
    }
}
