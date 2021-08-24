package com.travelsite.travellife.web.admin;

import  com.travelsite.travellife.po.Menu;
import com.travelsite.travellife.service.MenuService;
import com.travelsite.travellife.utils.db.StatusEnum;
import com.travelsite.travellife.utils.db.StatusUtil;
import com.travelsite.travellife.vo.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    /**
     * 跳转到列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:menu:index")
    public String index() {
        return "/system/menu/index";
    }

    /**
     * 菜单数据列表
     */
    @GetMapping("/list")
    @RequiresPermissions("system:menu:index")
    @ResponseBody
    public ResultVo list() {
        List<Menu> list = menuService.getListBySortOk();
        return ResultVoUtil.success(list);
    }

    //获取除自身外本级排序菜单列表
    @GetMapping("/sortList/{pid}/{notId}")
    @RequiresPermissions({"system:menu:add", "system:menu:edit"})
    @ResponseBody
    public Map<Integer, String> sortList(
            @PathVariable(value = "pid", required = false) Long pid,
            @PathVariable(value = "notId", required = false) Long notId){
        // 本级排序菜单列表
        notId = notId != null ? notId : (long) 0;
        List<Menu> levelMenu = menuService.getListByPid(pid, notId);
        Map<Integer, String> sortMap = new TreeMap<>();
        for (int i = 1; i <= levelMenu.size(); i++) {
            sortMap.put(i, levelMenu.get(i - 1).getTitle());
        }
        return sortMap;
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping({"/add", "/add/{pid}"})
    @RequiresPermissions("system:menu:add")
    public String toAdd(@PathVariable(value = "pid", required = false) Menu pMenu, Model model) {
        model.addAttribute("pMenu", pMenu);
        return "/system/menu/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:menu:edit")
    public String toEdit(@PathVariable("id") Menu menu, Model model) {
        //获取直属父菜单
        Menu pMenu = menuService.getById(menu.getPid());
        model.addAttribute("menu", menu);
        model.addAttribute("pMenu", pMenu);
        return "/system/menu/add";
    }

    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:menu:delete")
    public String Delete(@PathVariable Long id) {
        menuService.delete(id);
        return "/system/menu/index";
    }
    //保存添加/修改的数据
    @PostMapping("/save")
    @RequiresPermissions({"system:menu:add", "system:menu:edit"})
    @ResponseBody
    public ResultVo save(Menu menu) {
         if (menu.getId() == null) {
            // 排序为空时，添加到最后
            if(menu.getSort() == null){
                Integer sortMax = menuService.getSortMax(menu.getPid());
                menu.setSort(sortMax !=null ? sortMax - 1 : 0);
            }
        }

        // 添加/更新全部上级序号
        Menu pMenu = menuService.getById(menu.getPid());
        menu.setPids(pMenu.getPids() + ",[" + menu.getPid() + "]");

        // 复制保留无需修改的数据
        if (menu.getId() != null) {
            Menu beMenu = menuService.getById(menu.getId());
            EntityBeanUtil.copyProperties(beMenu, menu);
        }

        // 排序功能
        Integer sort = menu.getSort();
        //排除当前的目录权限id
        Long notId = menu.getId() != null ? menu.getId() : 0;
        //获取当前菜单权限同级所有
        List<Menu> levelMenu = menuService.getListByPid(menu.getPid(), notId);
        levelMenu.add(sort, menu);

        for (int i = 1; i <= levelMenu.size(); i++) {
            levelMenu.get(i - 1).setSort(i);
        }
        menu.setCreateDate(new Date());
        // 保存数据
        menuService.save(levelMenu);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:menu:detail")
    public String toDetail(@PathVariable Long id, Model model) {
        Menu menu = menuService.getById(id);
        model.addAttribute("menu", menu);
        return "/system/menu/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:menu:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (menuService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }


}
