package com.travelsite.travellife.web.admin;


import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.service.ProductService;
import com.travelsite.travellife.service.TypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeService typeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @RequiresPermissions("admin:ProductManage")
    @GetMapping("/ProductManage")
    public String ProductPage(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                          Pageable pageable, Model model){
        model.addAttribute("page",productService.listProduct(pageable));
        return "/admin/admin_ProductManage";
    }


    @GetMapping("/ProductModify")
    public String ProductModify(){
        return "/admin/admin_ProductModify";
    }

    @RequiresPermissions("admin:product:add")
    @GetMapping("/ProductAdd")
    public String ProductAdd(Model model){
        model.addAttribute("types",typeService.listType());
        return "/admin/admin_ProductAdd";
    }

    @PostMapping("/ProductManage")
    public String post(@RequestParam String name,@RequestParam int typeid,@RequestParam int price, @RequestParam String description,@RequestParam int stock,
                       @RequestParam int recommend,@RequestParam int sale,@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws FileNotFoundException {
        PRODUCT product = new PRODUCT();
        product.setName(name);
        product.setType(typeService.getType(typeid));
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(stock);
        product.setRecommend(recommend);
        product.setSale(sale);
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        System.out.print(fileName);
        //重新生成文件名fileName = UUID.randomUUID()+suffixName;
        String filePath = "C:\\Users\\m1395\\Desktop\\project\\gitproject\\src\\main\\resources\\static\\image";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(new File(filePath+fileName));
            product.setImg(fileName);
            LOGGER.info("上传成功");
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        PRODUCT p = productService.saveProduct(product);
        PRODUCT product1 = productService.getProductByName(product.getName());
        if (product1 != null){
        }
        if (p == null){
            attributes.addFlashAttribute("message","添加失败");

        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/ProductManage";
    }
    private void setTypes(Model model){
        model.addAttribute("types",typeService.listType());
    }

    @RequiresPermissions("admin:product:update")
    @GetMapping("/ProductManage/{id}/ProductModify")
    public String editProduct(@PathVariable Long id, Model model){
        setTypes(model);
        model.addAttribute("product",productService.getProduct(id));
        return "/admin/admin_ProductModify";
    }

    @PostMapping("/ProductManage/{id}")
    public String modify(@RequestParam String name,@RequestParam int typeid,@RequestParam int price, @RequestParam String description,@RequestParam int stock,
                         @RequestParam int recommend,@RequestParam int sale,@RequestParam("file") MultipartFile file,@PathVariable Long id,RedirectAttributes attributes){
        PRODUCT product = productService.getProduct(id);
        product.setName(name);
        product.setType(typeService.getType(typeid));
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(stock);
        product.setRecommend(recommend);
        product.setSale(sale);
        String fileName = null;
        if (file.isEmpty()) {
            fileName = product.getImg();
        }else{
            fileName = file.getOriginalFilename();
        }
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名fileName = UUID.randomUUID()+suffixName;
        String filePath = "C:\\Users\\m1395\\Desktop\\project\\gitproject\\src\\main\\resources\\static\\image";
        try {
            file.transferTo(new File(filePath+fileName));
            product.setImg(fileName);
            LOGGER.info("上传成功");
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        PRODUCT p = productService.updateProduct(id,product);
        if(p == null){
            attributes.addFlashAttribute("message","添加失败");
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/ProductManage";
    }
    @RequiresPermissions("admin:product:delete")
    @GetMapping("/ProductManage/{id}/ProductDelete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        productService.deleteProduct(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/ProductManage";
    }

}
