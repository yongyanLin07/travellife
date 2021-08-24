package com.travelsite.travellife.service;

import com.travelsite.travellife.NotFoundException;
import com.travelsite.travellife.dao.MenuRepository;
import com.travelsite.travellife.po.Menu;
import com.travelsite.travellife.po.TYPE;
import com.travelsite.travellife.utils.db.StatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    @Transactional
    public Menu getById(Long id) {
        if (id == 0L){
            return new Menu(id, "顶级菜单", "");
        }
        return menuRepository.findById(id).orElse(null);
    }

    /**
     * 获取菜单列表数据
     */
    @Override
    public List<Menu> getListBySortOk() {
        Sort sort =  Sort.by(Sort.Direction.ASC, "type", "sort");
        return menuRepository.findAllByStatus(sort, StatusEnum.OK.getCode());
    }

    /**
     * 获取父菜单下排序最大值
     * @param pid 父菜单ID
     */
    @Override
    public Integer getSortMax(Long pid){
        return menuRepository.findSortMax(pid);
    }

    /**
     * 根据父级菜单ID获取本级除自身外全部菜单
     */
    @Override
    public List<Menu> getListByPid(Long pid, Long notId){
        Sort sort = Sort.by(Sort.Direction.ASC, "sort");
        return menuRepository.findByPidAndIdNot(sort, pid, notId);
    }


    @Override
    public Menu save(Menu menu){
        return menuRepository.save(menu);
    }


    @Override
    public List<Menu> save(List<Menu> menuList){
        return menuRepository.saveAll(menuList);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> ids){
        // 获取与之关联的所有菜单
        Set<Menu> treeMenus = new HashSet<>();
        List<Menu> menus = menuRepository.findByIdIn(ids);
        menus.forEach(menu -> {
            treeMenus.add(menu);
            //查找其所有子菜单
            treeMenus.addAll(menuRepository.findByPidsLikeAndStatus("%[" + menu.getId() + "]%", menu.getStatus()));
        });

        treeMenus.forEach(menu -> {
            // 删除菜单状态时，同时更新角色的权限
            if(statusEnum == StatusEnum.DELETE){
                // 取消外键联系
                menuRepository.cancelRoleJoin(menu.getId());
            }
            // 更新关联的菜单状态
            menu.setStatus(statusEnum.getCode());
        });

        return treeMenus.size() > 0;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Menu menu = menuRepository.findById(id).get();
        // 取消外键联系
        menuRepository.cancelRoleJoin(menu.getId());
        menuRepository.delete(menu);
    }

    @Override
    public Menu update(Long id, Menu menu) {
        Menu m = menuRepository.findById(id).get();
        if(m == null){
            throw new NotFoundException("不存在");
        }
        BeanUtils.copyProperties(menu,m);
        return menuRepository.save(m);
    }

}
