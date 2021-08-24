package com.travelsite.travellife.web;

import com.travelsite.travellife.po.CART;
import com.travelsite.travellife.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserOrderController {
    @Autowired
    private CartService cartService;
    @GetMapping("/userOrder")
    public String userPage(Model model){
        List<CART> cartList = cartService.listCart();
        List<CART> clist = new ArrayList<>();
        for (int i = 0 ;i<cartList.size();i++){
            if(cartList.get(i).getCartValid() == 1){
                clist.add(cartList.get(i));
            }
        }
        model.addAttribute("clist",clist);
        return "userOrder";
    }
    @RequestMapping("/numupdate")
    public String UpdateNum(@RequestParam("count") Integer count,@RequestParam("id") Long id){
        CART cart = cartService.getCart(id);
        cart.setNum(count);
        cart.setPrice(count*cart.getProduct().getPrice());
        cartService.updateCart(id,cart);
        return "redirect:/userCart";
    }
    @RequestMapping("/orderselect")
    public String ToOrder(@RequestParam("cartidlist") String ids){
        String[] split = ids.split(",");
        for (int i =0;i<split.length;i++){
            Long id = Long.parseLong(split[i]);
            CART cart = cartService.getCart(id);
            cart.setCartValid(1);
            cartService.updateCart(id,cart);
        }
        return "redirect:/userOrder";
    }

}
