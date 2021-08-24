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
import java.util.Date;

//继承自HttpServletRequest的装饰类，重写改变HttpServletRequest的状态，
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
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
            encodedValues[i] = XSSDefend(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return XSSDefend(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return XSSDefend(value);
    }

//    把特殊字符转义
    private String XSSDefend(String value) {
        //You'll need to remove the spaces from the html entities below
        String value1 = value;
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*[j|J][a|A][v|V][a|A][s|S][c|C][r|R][i|I][p|P][t|T]:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("[e|E][v|V][a|A][l|L]\\((.*)\\)", "");
        value = value.replaceAll("[s|S][c|C][r|R][i|C][p|P][t|T]", "");

        //有xss行为
        if (!value.equals(value1)) {
            //记录xss 错误信息
            BlistService bean = SpringContextUtil.getBean(BlistService.class);
            ServletRequest request = getRequest();
            //获取用户IP
            String ip = IpUtils.getIPAddress((HttpServletRequest) request);
            BLIST blist = bean.getBlistByIpAndType(ip,2);

            if (blist != null) {
                return value;
            }else{
                blist = new BLIST();
                blist.setType(2);
                blist.setIpaddress(ip);
                blist.setTime(new Date());
            }

            bean.saveBlist(blist);
        }

        return value;
    }

}
