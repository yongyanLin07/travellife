package com.travelsite.travellife.web.admin;

import com.travelsite.travellife.aspect.LogMethod;
import com.travelsite.travellife.po.BLIST;
import com.travelsite.travellife.service.BlistService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class BlistController {
    @Autowired
    private BlistService blistService;

    @LogMethod( "查看黑名单")
    @RequiresPermissions("system:blist:index")
    @GetMapping("/BlistManage")
    public String BlistPage(@PageableDefault(size = 5,sort = {"ipaddress"},direction = Sort.Direction.DESC)
                                        Pageable pageable, Model model)
    {
        model.addAttribute("page",blistService.listBlist(pageable));
        return "/admin/admin_BlistManage";
    }
    @LogMethod( "删除黑名单")
    @RequiresPermissions("system:blist:delete")
    @GetMapping("/BlistManage/{ip}/BlistDelete")
    public String delete(@PathVariable String ip, RedirectAttributes attributes, HttpSession session){
        blistService.deleteBlist(ip);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/BlistManage";
    }
}
