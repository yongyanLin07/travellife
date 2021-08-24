package com.travelsite.travellife.web.admin;

import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.RoleService;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.shiro.ShiroUtil;
import com.travelsite.travellife.utils.db.AdminConst;
import com.travelsite.travellife.utils.db.ResultEnum;
import com.travelsite.travellife.utils.db.ResultException;
import com.travelsite.travellife.vo.ResultVo;
import com.travelsite.travellife.vo.ResultVoUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到角色分配页面
     */
    @GetMapping("/role")
    @RequiresPermissions("system:user:role")
    public String toRole(@RequestParam(value = "ids") USER user, Model model) {
        // 获取指定用户角色列表
        Set<Role> authRoles = user.getRoles();
        // 获取全部角色列表
        Sort sort = Sort.by(Sort.Direction.ASC, "createDate");
        List<Role> list = roleService.getListBySortOk(sort);

        model.addAttribute("id", user.getId());
        model.addAttribute("list", list);
        model.addAttribute("authRoles", authRoles);
        return "/system/user/role";
    }

    /**
     * 保存角色分配信息
     */
    @PostMapping("/role")
    @RequiresPermissions("system:user:role")
    @ResponseBody
    public ResultVo auth(
            @RequestParam(value = "id", required = true) USER user,
            @RequestParam(value = "roleId", required = false) HashSet<Role> roles) {
        // 禁止操作管理员数据
        if (user.getId().equals(AdminConst.ADMIN_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }
        // 更新用户角色
        user.setRoles(roles);
        // 保存数据
        userService.saveUser(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }

}
