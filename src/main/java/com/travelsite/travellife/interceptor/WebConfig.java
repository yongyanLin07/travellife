package com.travelsite.travellife.interceptor;


import com.travelsite.travellife.config.SqlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    PagesecInterceptor pagesecInterceptor;
    @Autowired
    BlistInterceptor blistInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pagesecInterceptor)
                .excludePathPatterns("/**/*.*","/**/*.js","/**/*.css","/error","/**/*.ico")
                .addPathPatterns("/**");
        registry.addInterceptor(blistInterceptor)
                .excludePathPatterns("/login","/**/*.js","/**/*.css","/error","/**/*.ico")
                .excludePathPatterns("/admin/**","/system/**","/**/*.*")
                .addPathPatterns("/**");
    }
}
