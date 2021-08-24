package com.travelsite.travellife.service;

import com.travelsite.travellife.po.Menu;
import com.travelsite.travellife.utils.db.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.LongFunction;

public interface MenuService {


    /**
     * 根据菜单ID查询菜单数据
     * @param id 菜单ID
     * @return 菜单信息
     */
    Menu getById(Long id);


    /**
     * 获取菜单列表数据
     * @return 菜单列表
     */
    List<Menu> getListBySortOk();

    /**
     * 获取排序最大值
     * @param pid 父菜单ID
     * @return 最大值
     */
    Integer getSortMax(Long pid);

    /**
     * 根据父级菜单ID获取本级全部菜单
     * @param pid 父菜单ID
     * @param notId 需要排除的菜单ID
     * @return 菜单列表
     */
    List<Menu> getListByPid(Long pid, Long notId);

    /**
     * 保存菜单
     * @param menu 菜单实体类
     * @return 菜单信息
     */
    Menu save(Menu menu);

    /**
     * 保存多个菜单
     * @param menuList 菜单实体类列表
     * @return 菜单列表
     */
    List<Menu> save(List<Menu> menuList);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     * @param statusEnum 数据状态
     * @param idList 数据ID列表
     * @return 操作结果
     */
    //    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    void delete(Long id);

    Menu update(Long id,Menu menu);
}
