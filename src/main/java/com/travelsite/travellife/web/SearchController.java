package com.travelsite.travellife.web;


import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private ProductService productService;

    @PostMapping("/search")
    public String search(@RequestParam String searchvalue, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        List<PRODUCT> list = productService.list();
        for (int i = 0 ;i<list.size();i++){
            if (searchvalue.contains(list.get(i).getName()) || searchvalue.equalsIgnoreCase(list.get(i).getName()))
            {
                Long id = list.get(i).getId();
                return "redirect:/ProductDetails/"+id;
            }
        }
        PrintWriter out = response.getWriter();
        out.print("<script>");
        out.print("alert('未搜索到相关产品');");
        out.print("window.location.href='/';");
        out.print("</script>");
        out.close();
        return "redirect:/";
    }
}
