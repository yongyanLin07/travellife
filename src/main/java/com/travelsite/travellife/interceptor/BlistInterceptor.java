package com.travelsite.travellife.interceptor;

import com.travelsite.travellife.po.BLIST;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.BlistService;
import com.travelsite.travellife.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Component
public class BlistInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private BlistService blistService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String ip = IpUtils.getIPAddress(request);

        List<BLIST> list = blistService.getlist();
        if(list.size()>0){
            for(int i =0;i<list.size();i++){
                if (list.get(0).getIpaddress().equals(ip)){
                PrintWriter out = response.getWriter();
                out.print("<script>");
                out.print("alert('涉及攻击行为，该IP地址已被封禁');");
                out.print("window.location.href='/login';");
                out.print("</script>");
                out.close();
                }
            }
            return false;
            }
        return true;
        }

    }


