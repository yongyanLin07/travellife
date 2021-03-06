package com.travelsite.travellife.web.admin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travelsite.travellife.po.PAGESEC;
import com.travelsite.travellife.service.PagesecService;
import com.travelsite.travellife.utils.MD5FileUtil;
import com.travelsite.travellife.vo.ResultVo;
import com.travelsite.travellife.vo.ResultVoUtil;


@Controller
@RequestMapping("/admin")
public class    PageSecurityController {

    @Autowired
    private PagesecService pagesecService;

    @RequiresPermissions("admin:pagesecurity:index")
    @GetMapping("/pagesecurity")
    public String PageManage(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC)
                                     Pageable pageable, Model model){
        Page<PAGESEC> pagesecs = pagesecService.listPage(pageable);
        model.addAttribute("page",pagesecs);
        return "/admin/page_index";
    }

    @RequiresPermissions("admin:pagesecurity:add")
    @GetMapping("/pagesecurity/add")
    public String PageAdd(Model model){
        model.addAttribute("errmsg","");
        return "/admin/page_add";
    }

    @PostMapping("/pagesecurity/save")
    @ResponseBody
    public ResultVo post(@Valid PAGESEC page, Model model, RedirectAttributes attributes){
        PAGESEC page1 = pagesecService.getPageByUrl(page.getUrl());
        if (page1 != null){
            model.addAttribute("pagesec",page);
            model.addAttribute("errmsg","URL??????????????????????????????URL");
            return ResultVoUtil.error(500, "URL??????????????????????????????URL");
        }
        if("??????".equals(page.getStatus())){//????????????
            page = openSec(page);
        }
        PAGESEC t = pagesecService.savePage(page);
        if (t == null){
            attributes.addFlashAttribute("message","????????????");
            return ResultVoUtil.error(500, "????????????");

        }else {
            attributes.addFlashAttribute("message","????????????");
        }
        return ResultVoUtil.SAVE_SUCCESS;
    }
    @RequiresPermissions("admin:pagesecurity:update")
    @GetMapping("/pagesecurity/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model){
        model.addAttribute("errmsg","");
        model.addAttribute("pagesec",pagesecService.getPage(id));
        return "/admin/page_edit";
    }
    @PostMapping("/pagesecurity/update/{id}")
    @ResponseBody
    public ResultVo post(@Valid PAGESEC page, Model model,@PathVariable Integer id,RedirectAttributes attributes){
        PAGESEC page1 = pagesecService.getPageByUrl(page.getUrl());
        PAGESEC page2 = pagesecService.getPage(page.getId());
        if (page1 != null && !page1.getUrl().equals(page2.getUrl())){
            model.addAttribute("pagesec",page);
            model.addAttribute("errmsg","URL??????????????????????????????URL");
            return ResultVoUtil.error(500, "URL??????????????????????????????URL");
        }
        if("??????".equals(page.getStatus())){//????????????
            page = openSec(page);
        }
        PAGESEC t = pagesecService.updatePage(id,page);
        if (t == null){
            attributes.addFlashAttribute("message","????????????");
            return ResultVoUtil.error(500, "????????????");
        }else {
            attributes.addFlashAttribute("message","????????????");

        }
        return ResultVoUtil.SAVE_SUCCESS;
    }
    @RequiresPermissions("admin:pagesecurity:delete")
    @GetMapping("/pagesecurity/delete/{id}")
    public String delete(@PathVariable Integer id,RedirectAttributes attributes){
        pagesecService.deletePage(id);
        attributes.addFlashAttribute("message","????????????");
        return "redirect:/admin/pagesecurity";
    }

    //????????????
    private PAGESEC openSec(PAGESEC page){
        //????????????classpath????????????
        URL uri = ClassLoader.getSystemResource("");///C:/Users/m1395/Desktop/project/gitproject/target/classes/
        System.out.println(uri);
        //??????????????????
        String classes = URLDecoder.decode(uri.getPath());///C:/Users/m1395/Desktop/project/gitproject/target/classes/
        String path = page.getPath();
        if(!path.startsWith("/")&&!path.startsWith("\\")){
            path = "\\"+path;
        }
        System.err.println("?????????????????????"+path+"???...????????????");
        File htmlFile = new File(classes + "templates" + path);
        if(!htmlFile.exists()){
            System.err.println("???????????????"+path+"??????????????????????????????????????????");
        }
        try {
            String md5str = MD5FileUtil.getFileMD5String(htmlFile);
            page.setMd5str(md5str);
            String pageDir = classes+"com\\travelsite\\travellife\\page";//??????????????????????????????
            FileUtil.copyFile(htmlFile,new File(pageDir+path));
            System.err.println("???????????????"+path+"???...????????????");
        } catch (IOException e) {
            System.err.println("?????????????????????"+path+"???????????????...");
            e.printStackTrace();
        }
        return page;
    }
}

