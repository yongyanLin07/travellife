package com.travelsite.travellife.web.admin;


import com.travelsite.travellife.po.Menu;
import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.MenuService;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.shiro.ShiroUtil;
import com.travelsite.travellife.utils.db.AdminConst;
import com.travelsite.travellife.utils.db.MenuTypeEnum;
import com.travelsite.travellife.utils.db.StatusEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    MenuService menuService;

    @RequestMapping
    public String Logging() {
        return "admin/AdminLogin";
    }

    @RequestMapping("logout")
    public String logout() {
        return "redirect:/admin";
    }

        /**
         * shiro登陆
         * @param name
         * @param password
         * @param session
         * @param attributes
         * @param model
         * @param response
         * @param request
         * @return
         * @throws IOException
         */
        @PostMapping("/AdminLogin")
        public String login (@RequestParam String name,
                @RequestParam String password,
                HttpSession session, RedirectAttributes attributes,
                Model model,
                HttpServletResponse response, HttpServletRequest request) throws IOException {
            // 获取Subject主体对象
            Subject subject = SecurityUtils.getSubject();
            // 封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(name, password);
            String msg = "用户名密码错误";
            try {
                subject.login(token);
                USER user = ShiroUtil.getSubject();
                session.setAttribute("user",user);
                return "redirect:/admin/index";
            } catch (LockedAccountException e) {
                msg = e.getMessage();
            } catch (Exception e) {
            }
            attributes.addFlashAttribute("message", msg);
            return "redirect:/admin";
        }
    @GetMapping("index")
    public String index(Model model) {
// 判断是否拥有后台角色
        USER user = ShiroUtil.getSubject();

        //确认用户存在
        if (user != null) {

            model.addAttribute("user", user);
            //到main頁面
            //菜单键值对(ID->菜单)
            Map<Long, Menu> keyMenu = new HashMap<>(16);
            // 更新管理员菜单
            if (user.getId().equals(AdminConst.ADMIN_ID)) {
                List<Menu> menus = menuService.getListBySortOk();
                menus.forEach(menu -> keyMenu.put(menu.getId(), menu));
            } else {
                // 其他用户根据自身相应的角色中获取菜单资源
                Set<Role> roles = ShiroUtil.getSubjectRoles();
                roles.forEach(role -> {
                    role.getMenus().forEach(menu -> {
                        if (menu.getStatus().equals(StatusEnum.OK.getCode())) {
                            keyMenu.put(menu.getId(), menu);
                        }
                    });
                });
            }

            // 封装菜单树形数据
            Map<Long, Menu> treeMenu = new HashMap<>(16);
            keyMenu.forEach((id, menu) -> {
                //treemenu中放入目录类型菜单
                if (!menu.getType().equals(MenuTypeEnum.BUTTON.getCode())) {
                    //拥有父菜单的文件类型菜单权限存入相应父菜单中
                    if (keyMenu.get(menu.getPid()) != null) {
                        keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
                    } else {
                        //目录类型菜单存入treemenu
                        if (menu.getType().equals(MenuTypeEnum.DIRECTORY.getCode())) {
                            treeMenu.put(Long.valueOf(menu.getSort()), menu);
                        }
                    }
                }
            });

            model.addAttribute("treeMenu", treeMenu);

            return "admin/main";
        }
        return "redirect:/admin";
    }
        @GetMapping("logout")
        public String logout (HttpSession session){
            session.removeAttribute("user");
            SecurityUtils.getSubject().logout();
            return "redirect:/admin";
        }
        @GetMapping("/adminPage")
        public String ToAdminPage(HttpSession session,Model model){
            USER user = (USER)session.getAttribute("user");
            //解密邮箱
            user = userService.getUser(user);
            model.addAttribute("user",user);
          return "/admin/adminPage";
    }
    }
