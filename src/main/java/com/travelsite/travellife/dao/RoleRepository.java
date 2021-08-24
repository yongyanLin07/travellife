package com.travelsite.travellife.dao;

import com.travelsite.travellife.po.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Set;


public interface RoleRepository extends BaseRepository<Role,Long> {


    /**
     * 查找相应状态的角色
     */
    List<Role> findAllByStatus(Sort sort, Byte status);

    /**
     * 查询指定用户的角色列表
     */
    Set<Role> findByUsers_IdAndStatus(Long id, Byte status);

    /**
     * 根据标识查询角色数据,且排查指定ID的角色
     */
    Role findByNameAndIdNot(String name, Long id);

    /**
     * 判断指定的用户是否存在角色
     */
    Boolean existsByUsers_IdAndStatus(Long id, Byte status);

    /**
     * 取消角色与用户之间的关系
     */
    @Modifying
    //    @Transactional
    @Query(value = "DELETE FROM sys_user_role WHERE role_id in ?1", nativeQuery = true)
    Integer cancelUserJoin(List<Long> ids);

    /**
     * 取消角色与菜单之间的关系
     */
    @Modifying
    //    @Transactional
    @Query(value = "DELETE FROM sys_role_menu WHERE role_id in ?1", nativeQuery = true)
    Integer cancelMenuJoin(List<Long> ids);

}
