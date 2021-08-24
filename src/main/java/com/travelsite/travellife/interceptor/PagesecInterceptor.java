package com.travelsite.travellife.interceptor;


import com.travelsite.travellife.dao.PagesecRepository;
import com.travelsite.travellife.po.PAGESEC;
import com.travelsite.travellife.utils.MD5FileUtil;
import org.aspectj.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Pattern;

@Component
public class PagesecInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    PagesecRepository pagesecRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        URL uri = ClassLoader.getSystemResource("");
        String classes = URLDecoder.decode(uri.getPath());
        String requestURI = request.getRequestURI();

        System.out.println("================》当前访问路径："+requestURI);
        if(requestURI.contains(";")){
            requestURI=requestURI.substring(0,requestURI.lastIndexOf(";"));
        }

        String[] uris = requestURI.split("/");

        if(uris.length>0&&isNumeric(uris[uris.length-1])){
            requestURI=requestURI.substring(0,requestURI.lastIndexOf("/"));
        }

        PAGESEC pagesec = pagesecRepository.findByUrl(requestURI);
        if(pagesec!=null&&"开启".equals(pagesec.getStatus())){
            String pathStr = pagesec.getPath();
            System.err.println("请求路径【"+pagesec.getUrl()+"】开启了网页防篡改功能...");
            if(!pathStr.startsWith("\\")&&!pathStr.startsWith("/")){
                pathStr = "\\"+pathStr;
            }
            File currentFile = new File(classes + "templates" + pathStr);
            if(!currentFile.exists()){
                System.err.println("资源文件【"+pathStr+"】不存在，请检查路径是否正确");
            }
            /** 1.当前请求资源文件的MD5字符串 */
            String curMd5 = MD5FileUtil.getFileMD5String(currentFile);
            /** 2.校验文件是否被篡改，判断文件MD5加密字符串是否一致 */
            if(curMd5.equals(pagesec.getMd5str())){
                System.err.println("请求路径【"+pagesec.getUrl()+"】的资源文件校验成功...");
                return true;
            }
            /** 3.MD5字符串不一致，说明资源文件已被篡改，恢复文件 */
            System.err.println("请求路径【"+pagesec.getUrl()+"】的资源文件校验失败，已被篡改，开始恢复文件...");
            String pageDir = classes+"com\\travelsite\\travellife\\page";
            File oldFile = new File(pageDir + pathStr);
            if(!oldFile.exists()){
                System.err.println("请求路径【"+pagesec.getUrl()+"】的原资源文件不存在，请重启服务器...");
                return true;
            }
            FileUtil.copyFile(oldFile,currentFile);
            System.err.println("请求路径【"+pagesec.getUrl()+"】的资源文件恢复成功...");
        }
        return true;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
