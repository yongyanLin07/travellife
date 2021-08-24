package com.travelsite.travellife.config;


import com.travelsite.travellife.po.BLIST;
import com.travelsite.travellife.service.BlistService;
import com.travelsite.travellife.service.BlistServiceImpl;
import com.travelsite.travellife.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(XssFilter.class);
    FilterConfig filterConfig = null;
    @Autowired
    private BlistService blistService;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XssHttpServletRequestWrapper(
                (HttpServletRequest) request), response);
        HttpServletRequest request1 = (HttpServletRequest)request;


    }

}