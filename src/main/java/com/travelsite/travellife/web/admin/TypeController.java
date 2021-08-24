package com.travelsite.travellife.web.admin;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travelsite.travellife.po.TYPE;
import com.travelsite.travellife.service.TypeService;
import com.travelsite.travellife.vo.ResultVo;
import com.travelsite.travellife.vo.ResultVoUtil;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequiresPermissions("admin:CateManage")
    @GetMapping("/CateManage")
    public String TypeManage(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC)
                                         Pageable pageable, Model model, HttpSession session
                             ){
        //TYPE type = typeService.getType(39);
        //type = typeService.get(type);
      // session.setAttribute("type",type);
        model.addAttribute("page",typeService.listType(pageable));
        return "/admin/admin_cateManage";
    }

    @GetMapping("/CateModify")
    public String TypeModify(){
        return "/admin/admin_cateModify";
    }

    @RequiresPermissions("admin:cate:add")
    @GetMapping("/CateAdd")
    public String TypeAdd(){
        return "/admin/admin_cateAdd";
    }
    @PostMapping("/CateManage")
    @ResponseBody
    public ResultVo post(@Valid TYPE type, BindingResult result, RedirectAttributes attributes){
        
        TYPE type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name","nameError","不能重复添加分类");
            return ResultVoUtil.error(500, "不能重复添加分类");
        }
        TYPE t = typeService.saveType(type);
        if (t == null){
            attributes.addFlashAttribute("message","添加失败");
            return ResultVoUtil.error(500, "添加失败");

        }
        return ResultVoUtil.SAVE_SUCCESS;
    }
    @RequiresPermissions("admin:cate:update")
    @GetMapping("/CateManage/{id}/CateModify")
    public String editType(@PathVariable Integer id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "/admin/admin_cateModify";
    }
    @PostMapping("/CateManage/{id}")
    @ResponseBody
    public ResultVo post(@Valid TYPE type, BindingResult result,@PathVariable Integer id,RedirectAttributes attributes){
        TYPE type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name","nameError","不能重复添加分类");
            return ResultVoUtil.error(500, "不能重复添加分类");
        } 
        TYPE t = typeService.updateType(id,type);
        if (t == null){
            attributes.addFlashAttribute("message","添加失败");
            return ResultVoUtil.error(500, "添加失败");

        }else {
            attributes.addFlashAttribute("message","添加成功");

        }
        return ResultVoUtil.SAVE_SUCCESS;
    }
    @RequiresPermissions("admin:cate:delete")
    @GetMapping("/CateManage/{id}/CateDelete")
    public String delete(@PathVariable Integer id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/CateManage";
    }
}
