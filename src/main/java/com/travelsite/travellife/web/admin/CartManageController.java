package com.travelsite.travellife.web.admin;

import com.travelsite.travellife.service.CartService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class CartManageController {
    @Autowired
    private CartService cartService;
    @RequiresPermissions("admin:CartManage")
    @GetMapping("/CartManage")
    public String ToCartManage(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                       Pageable pageable, Model model)
    {
        model.addAttribute("page",cartService.listCart(pageable));
        return "admin/admin_CartManage";
    }
    @RequiresPermissions("admin:cart:delete")
    @GetMapping("/CartManage/{id}/CartDelete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        cartService.deleteCart(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/CartManage";
    }
}
