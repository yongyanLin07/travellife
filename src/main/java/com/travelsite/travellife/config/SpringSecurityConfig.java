package com.travelsite.travellife.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.Filter;


@Configuration//配置Spring容器
public class SpringSecurityConfig  {
    /**
     * 配置CSRF，XSS过滤器
     */
    @Bean
    public FilterRegistrationBean<MyCsrfFilter> csrfFilter() {
        FilterRegistrationBean<MyCsrfFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MyCsrfFilter(new HttpSessionCsrfTokenRepository()));
        registration.addUrlPatterns("/*");
        registration.setName("csrfFilter");
        return registration;
    }
    @Bean
    public Filter XssFilter(){
        return new XssFilter();
    }
    @Bean
    public Filter SqlFilter(){return new SqlFilter();}
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter((XssFilter) this.XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        return registration;
    }
    @Bean
    public FilterRegistrationBean<SqlFilter> sqlFilter() {
        FilterRegistrationBean<SqlFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter((SqlFilter) this.SqlFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sqlFilter");
        return registration;
    }

}
