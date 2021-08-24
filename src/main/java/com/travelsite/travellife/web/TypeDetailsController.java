package com.travelsite.travellife.web;

import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.service.ProductService;
import com.travelsite.travellife.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TypeDetailsController {
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeService typeService;
    @GetMapping("TypeDetails")
    public String TypeDetail(){
        return "/TypeDetails";
    }

    @GetMapping("TypeDetails/{id}")
    public String TypeDetails(@PathVariable Integer id, Model model){
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
        return "/TypeDetails";
    }

    @GetMapping("TypeDetails/ProductDetails/{id}")
    public String ToProductDetails(@PathVariable Long id, Model model){
        PRODUCT product = productService.getProduct(id);
        model.addAttribute("product",product);
        model.addAttribute("types",typeService.listType());
        return "redirect:/ProductDetails/{id}";
    }
}
