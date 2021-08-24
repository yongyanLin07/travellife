package com.travelsite.travellife.web.admin;

import com.travelsite.travellife.po.Menu;
import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.service.MenuService;
import com.travelsite.travellife.service.RoleService;
import com.travelsite.travellife.shiro.ShiroUtil;
import com.travelsite.travellife.utils.db.*;
import com.travelsite.travellife.vo.EntityBeanUtil;
import com.travelsite.travellife.vo.ResultVo;
import com.travelsite.travellife.vo.ResultVoUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:role:index")
    public String index(Model model, @PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
            Pageable pageable){

        model.addAttribute("page", roleService.listRole(pageable));
        return "/system/role/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:role:add")
    public String toAdd(){
        return "/system/role/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:role:edit")
    public String toEdit(@PathVariable("id") Role role, Model model){
        model.addAttribute("role", role);
        return "/system/role/add";
    }

    /**
     * 保存添加/修改的数据
     * @param role 实体对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:role:add", "system:role:edit"})
    @ResponseBody
    public ResultVo save(Role role){
        // 不允许其余用户操作管理员角色数据
        if (role.getId() !=null && role.getId().equals(AdminConst.ADMIN_ROLE_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }

        // 判断角色名是否重复
        if (roleService.repeatByName(role)) {
            throw new ResultException(ResultEnum.ROLE_EXIST);
        }

        // 保留无需修改的数据
        if(role.getId() != null){
            Role beRole = roleService.getById(role.getId());
            String[] fields = {"users", "menus"};
            EntityBeanUtil.copyProperties(beRole, role, fields);
        }
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        // 保存数据
        roleService.save(role);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到授权页面
     */
    @GetMapping("/auth")
    @RequiresPermissions("system:role:auth")
    public String toAuth(@RequestParam(value = "ids") Long id, Model model){
        model.addAttribute("id", id);
        return "/system/role/auth";
    }

    /**
     * 获取权限资源列表
     */
    @GetMapping("/authList")
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    public ResultVo authList(@RequestParam(value = "ids") Role role){
        // 获取指定角色权限资源
        Set<Menu> authMenus = role.getMenus();
        // 获取全部菜单列表
        List<Menu> list = menuService.getListBySortOk();
        // 融合两项数据，得到该角色对应拥有的
        list.forEach(menu -> {
            if(authMenus.contains(menu)){
                //授权界面默认选中
                menu.setRemark("auth:true");
            }else {
                menu.setRemark("");
            }
        });
        return ResultVoUtil.success(list);
    }

    /**
     * 保存授权信息
     */
    @PostMapping("/auth")
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    public ResultVo auth(
            @RequestParam(value = "id", required = true) Role role,
            @RequestParam(value = "authId", required = false) HashSet<Menu> menus){
        // 不允许操作管理员角色数据
        if (role.getId().equals(AdminConst.ADMIN_ROLE_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }
        // 更新角色菜单
        role.setMenus(menus);
        // 保存数据
        roleService.save(role);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:role:detail")
    public String toDetail(@PathVariable("id") Role role, Model model){
        model.addAttribute("role", role);
        return "/system/role/detail";
    }

    /**
     * 跳转到拥有该角色的用户列表页面
     */
    @GetMapping("/userList/{id}")
    @RequiresPermissions("system:role:detail")
    public String toUserList(@PathVariable("id") Role role, Model model){
        model.addAttribute("list", role.getUsers());
        return "/system/role/userList";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:role:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids){
        // 不能修改超级管理员角色状态
        if(ids.contains(AdminConst.ADMIN_ROLE_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_STATUS);
        }
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (roleService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}
