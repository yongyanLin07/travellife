package com.travelsite.travellife.web;

import com.travelsite.travellife.po.CART;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.CartService;
import com.travelsite.travellife.service.ProductService;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.shiro.ShiroUtil;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserCartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @GetMapping("/ProductDetails/{id}/userPage")
    public String ProductToUserPage(@PathVariable Long id){
        return "userPage";
    }
    @GetMapping("/TypeDetails/{id}/userPage")
    public String TypeToUserPage(@PathVariable int id){
                return "userPage";
    }
    @PostMapping("/LoginUserManage/{id}")
    public String LoginUserModify(@Valid USER user,BindingResult result,@PathVariable Long id, RedirectAttributes attributes, HttpSession session){
        USER user1 = userService.getUserByName(user.getName());
        if (user1 != null){
            result.rejectValue("name","nameError","不能重复添加分类");
        }
        USER u = userService.updateUser(user,id);
        if (u == null){
            attributes.addFlashAttribute("message","添加失败");
        }else {
            attributes.addFlashAttribute("message","添加成功");
            session.removeAttribute("user");
            session.setAttribute("user",u);
        }
        return "userPage";
    }
    @GetMapping("/userCart")
    public String getUserCart(Model model, HttpSession session)
    {
        List<CART> cartList =cartService.listCart();
        List<CART> carts = new ArrayList<>();
        USER user = ShiroUtil.getSubject();
        for(int i = 0;i<cartList.size();i++){
            CART cart = cartList.get(i);
            if (cart.getUser().getId() == user.getId().intValue())
            {
                carts.add(cart);
            }
        }
        model.addAttribute("carts",carts);
        return "/userCart";
    }


}
