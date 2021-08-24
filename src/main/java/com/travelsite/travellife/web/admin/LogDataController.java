package com.travelsite.travellife.web.admin;

import com.travelsite.travellife.aspect.LogMethod;
import com.travelsite.travellife.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LogDataController {
    @Autowired
    private LogService logService;

    //@LogMethod("查看日志管理")
    @RequiresPermissions("system:log:index")
    @GetMapping("/LogManage")
    public String LogPage(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model)
    {
        model.addAttribute("page",logService.listLogData(pageable));
        return "/admin/admin_LogManage";
    }
    @RequiresPermissions("system:log:delete")
    @GetMapping("/LogManage/{id}/LogDelete")
    public String delete(@PathVariable int id, RedirectAttributes attributes, HttpSession session){
        logService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/LogManage";
    }

}
