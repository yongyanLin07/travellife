package com.travelsite.travellife.web;

import com.travelsite.travellife.po.CART;
import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.CartService;
import com.travelsite.travellife.service.ProductService;
import com.travelsite.travellife.service.UserService;
import com.travelsite.travellife.shiro.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {


    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @PostMapping("/ProductDetails/CartPage/{id}")
    public String AddToCart(@Valid CART cart, BindingResult result, @PathVariable Long id,RedirectAttributes attributes, HttpSession session, HttpServletResponse response) throws IOException {
        PRODUCT product = productService.getProduct(id);
        int stock = product.getStock();
        USER user = ShiroUtil.getSubject();
        if(user == null){
            PrintWriter out = response.getWriter();
            out.write("<script>");
            out.write("alert('Login,Please');");
            out.write("</script>");
            return "redirect:/login";
        }
        CART c = cartService.getByUserProduct(user,product);
        if (stock < cart.getNum()){
            attributes.addFlashAttribute("message","添加失败");
            PrintWriter out = response.getWriter();
            out.write("<script>");
            out.write("alert('库存不足,加入购物车失败');");
            out.write("</script>");
            out.close();
            return "/ProductDetails/{id}";
        }else if(c != null){

            int price1=cart.getNum()*product.getPrice();
            cart.setPrice(c.getPrice()+price1);
            cart.setNum(cart.getNum()+c.getNum());
            product.setStock(stock-cart.getNum());
            cart.setId(c.getId());
            cart.setProduct(product);
            cart.setUser(user);
            productService.updateProduct(id,product);
            cartService.updateCart(cart.getId(),cart);
            return "redirect:/ProductDetails/{id}";
        }
        else {
            cart.setUser(user);
            int totalPrice = cart.getNum() * product.getPrice();
            cart.setPrice(totalPrice);
            product.setStock(stock-cart.getNum());
            cart.setProduct(product);
            productService.updateProduct(id,product);
            cartService.saveCart(cart);
            attributes.addFlashAttribute("message","添加成功");
            return "redirect:/ProductDetails/{id}";
        }

    }
    @GetMapping("/CartDelete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        cartService.deleteCart(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/userCart";
    }

}
