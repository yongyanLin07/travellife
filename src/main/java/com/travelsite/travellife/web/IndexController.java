package com.travelsite.travellife.web;

import com.travelsite.travellife.service.ProductService;
import com.travelsite.travellife.service.TypeService;
import com.travelsite.travellife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeService typeService;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("products",productService.list());
        model.addAttribute("types",typeService.listType());
        return "index";
    }
    @GetMapping("/contact")
    public String contact(Model model){
        model.addAttribute("products",productService.list());
        model.addAttribute("types",typeService.listType());
        return "contact";
    }
    @GetMapping("/noAuth")
    public String tonoAuth(){
        return "/admin/main/noAuth";
    }
    @GetMapping("/service")
    public String service(Model model){
        model.addAttribute("products",productService.list());
        model.addAttribute("types",typeService.listType());
        return "service";
    }
    @GetMapping("/sale")
    public String sale(Model model){
        model.addAttribute("products",productService.list());
        model.addAttribute("types",typeService.listType());
        return "sale";
    }
    @GetMapping("/csrf")
    public String csrf(){
        return "csrfAttack";
    }
    @GetMapping("/xss")
    public String xss(){
        return "XSSAttack";
    }
}
