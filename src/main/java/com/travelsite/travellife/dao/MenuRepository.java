package com.travelsite.travellife.dao;

import com.travelsite.travellife.po.Menu;
import com.travelsite.travellife.utils.db.StatusConst;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MenuRepository extends BaseRepository<Menu, Long> {

    /**
     * 查找多个菜单
     * @param ids id列表
     * @return 菜单列表
     */
     List<Menu> findByIdIn(List<Long> ids);

    /**
     * 查找响应状态的菜单
     */
    List<Menu> findAllByStatus(Sort sort, Byte status);


    Menu findByUrl(String url);

    /**
     * 根据父ID查找子菜单
     * @return 菜单列表
     */
    List<Menu> findByPidsLikeAndStatus(String pids, Byte status);

    /**
     * 获取排序最大值
     * @param pid 父菜单ID
     * @return 最大值
     */
    @Query("select max(sort) from Menu m where m.pid = ?1 and m.status <> " + StatusConst.DELETE)
    Integer findSortMax(long pid);

    /**
     * 根据父级菜单ID获取本级全部菜单
     * @param sort 排序对象
     * @param pid 父菜单ID
     * @param notId 需要排除的菜单ID
     * @return 菜单列表
     */
     List<Menu> findByPidAndIdNot(Sort sort, long pid, long notId);

    /**
     * 取消菜单与角色之间的关系
     * @param id 菜单ID
     * @return 影响行数
     */
    @Modifying
    //    @Transactional
    @Query(value = "DELETE FROM sys_role_menu WHERE menu_id = ?1", nativeQuery = true)
    Integer cancelRoleJoin(Long id);

}
