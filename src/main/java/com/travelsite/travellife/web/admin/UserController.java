package com.travelsite.travellife.web.admin;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.shiro.RetryLimitHashedCredentialsMatcher;
import com.travelsite.travellife.vo.ResultVo;
import com.travelsite.travellife.vo.ResultVoUtil;


@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher;
    /**
     * 解除admin 用户的限制登录
     */
    @RequestMapping("/unlockAccount")
    public String unlockAccount(Model model){
        model.addAttribute("msg","用户解锁成功");

        retryLimitHashedCredentialsMatcher.unlockAccount("admin");

        return "login";
    }


    @GetMapping("/UserManage")
    public String ProductPage(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model){
        Page<USER> page = userService.listUser(pageable);
        model.addAttribute("page",page);
        return "/admin/admin_userManage";
    }

    @GetMapping("/UserModify")
    public String ProductModify(){

        return "/admin/admin_userModify";
    }
    @GetMapping("/UserAdd")
    public String ProductAdd(){

        return "/admin/admin_userAdd";
    }
    @PostMapping("/UserManage")
    @ResponseBody
    public ResultVo post(@Valid USER user, BindingResult result, RedirectAttributes attributes){
    	try{
        USER user1 = userService.getUserByName(user.getName());
        if (user1 != null){
            result.rejectValue("name","nameError","不能重复添加分类");
        } 
        USER u = userService.saveUser(user);
        if (u == null){
            attributes.addFlashAttribute("message","添加失败");

        }else {
            attributes.addFlashAttribute("message","添加成功");

        }
        return ResultVoUtil.SAVE_SUCCESS;
        }catch(Exception e){
        	return ResultVoUtil.error(500, "保存失败");
        }
    }
    @GetMapping("/UserManage/{id}/UserModify")
    public String editProduct(@PathVariable Long id,Model model){
        model.addAttribute("user",userService.getUserById(id));

        return "/admin/admin_userModify";
    }

    @PostMapping("/UserManage/{id}")
    public String post(USER user, BindingResult result,@PathVariable Long id,RedirectAttributes attributes,HttpSession session,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        USER user1 = userService.getUserByName(user.getName());
        if (user1 != null){
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('用户名重复');");
            out.print("window.location.href='/login';");
            out.print("</script>");
            out.close();
        }
        USER u = userService.updateUser(user,id);
        if (u == null){
            attributes.addFlashAttribute("message","添加失败");

        }else {
            attributes.addFlashAttribute("message","添加成功");
            session.removeAttribute("user");
            session.setAttribute("user",u);
        }
        return "redirect:/admin/adminPage";
    }
    @GetMapping("/UserManage/{id}/UserDelete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        userService.deleteUser(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/UserManage";
    }
 
}
