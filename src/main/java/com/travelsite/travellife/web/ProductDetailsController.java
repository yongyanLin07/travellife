package com.travelsite.travellife.web;

import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.service.ProductService;
import com.travelsite.travellife.service.TypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductDetailsController {
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CartController cartController;

    @RequiresPermissions("ProductDetails")
    @GetMapping("/ProductDetails/{id}")
    public String ProductDetails(@PathVariable Long id, Model model){
        PRODUCT product = productService.getProduct(id);
        model.addAttribute("product",product);
        model.addAttribute("types",typeService.listType());
        return "/ProductDetails";
    }
    @GetMapping("/ProductDetails/TypeDetails/{id}")
    public String ToTypeDetails(@PathVariable Integer id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("typeItem",typeService.getType(id));
        List<PRODUCT> list = productService.list();
        List<PRODUCT> productList = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            PRODUCT p = list.get(i);
            if (p.getType().getId() == id){
                productList.add(p);
            }
        }
        model.addAttribute("products",productList);
        return "redirect:/TypeDetails/{id}";
    }
}
