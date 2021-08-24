package com.travelsite.travellife.web;

import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String Register(){
        return "/register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String sex, @RequestParam String email, @RequestParam String password,
                           HttpSession session, RedirectAttributes attribute , HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        USER u = new USER();
        u.setName(name);
        u.setEmail(email);
        u.setSex(sex);
        u.setStatus(0);
        u.setPassword(password);
        u.setSalt("");

        USER user1 = userService.getUserByName(u.getName());

        if (user1 != null){
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('用户名重复');");
            out.write("window.location.href='/register';");
            out.print("</script>");
            out.close();
        }
        if (u == null){
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('添加失败');");
            out.write("window.location.href='/register';");
            out.print("</script>");
            out.close();
        }else {
            userService.saveUser(u);
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('添加成功');");
            out.write("window.location.href='/login';");
            out.print("</script>");
            out.close();

        }
        return "/login";
    }
}
