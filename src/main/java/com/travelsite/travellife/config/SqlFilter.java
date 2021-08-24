package com.travelsite.travellife.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SqlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new SqlHttpServletRequestWrapper(
                (HttpServletRequest) request), response);
    }
}
