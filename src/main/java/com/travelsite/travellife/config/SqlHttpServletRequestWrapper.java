package com.travelsite.travellife.config;

import com.travelsite.travellife.po.BLIST;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.BlistService;
import com.travelsite.travellife.shiro.ShiroUtil;
import com.travelsite.travellife.shiro.SpringContextUtil;
import com.travelsite.travellife.utils.IpUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SqlHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public SqlHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values==null)  {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = SQLDefend(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return SQLDefend(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return SQLDefend(value);
    }

    //    把特殊字符转义
    private String SQLDefend(String value) {
        //You'll need to remove the spaces from the html entities below
        String value1 = value;
        value = value.replaceAll("'", "");
        value = value.replaceAll("&", "");
        //有SQL注入行为
        if (!value.equals(value1)) {
            //记录xss 错误信息
            BlistService bean = SpringContextUtil.getBean(BlistService.class);
            ServletRequest request = getRequest();
            //获取用户IP
            String ip = IpUtils.getIPAddress((HttpServletRequest) request);
            BLIST blist = bean.getBlistByIpAndType(ip,1);

            if (blist != null) {
                return value;
            }else{
                blist = new BLIST();
                blist.setType(1);
                blist.setIpaddress(ip);
                blist.setTime(new Date());
            }

            bean.saveBlist(blist);
        }

        return value;
    }

}
