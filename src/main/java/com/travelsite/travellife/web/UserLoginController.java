package com.travelsite.travellife.web;

import com.travelsite.travellife.po.BLIST;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.BlistService;
import com.travelsite.travellife.service.ProductService;
import com.travelsite.travellife.service.TypeService;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class UserLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeService typeService;

    @PostMapping("/login")
    public String logging(@RequestParam String name, @RequestParam String password, HttpSession session, RedirectAttributes attributes, Model model, HttpServletRequest request
    , HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");


        USER user = new USER();
        //未经认证的主体
        Subject subject = SecurityUtils.getSubject();
        //收集登录凭证信息
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            //实际上交由Security Manager.login()
            subject.login(token);
            //登录成功拿到当前用户
            user = ShiroUtil.getSubject();
            session.setAttribute("user",user);
            model.addAttribute("types",typeService.listType());
            model.addAttribute("products",productService.list());
            return "/index";
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('用户名或密码错误');");
            out.print("window.location.href='/login';");
            out.print("</script>");
            out.close();
        }
        return "/login";
    }
    @GetMapping("userPage")
    public String ToUserPage(HttpSession session,Model model){
        USER user = ShiroUtil.getSubject();
        //解密邮箱
        user = userService.getUser(user);
        model.addAttribute("user",user);
        return "/userPage";
    }
    @GetMapping("/login")
    public String login(){
        return "/login";
    }
    @GetMapping("/userLogout")
    public String UserLogout(HttpSession session){
        SecurityUtils.getSubject().logout();
        //session.removeAttribute("user");
        return "redirect:/";
    }
}
