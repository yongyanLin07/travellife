package com.travelsite.travellife.config;

import com.travelsite.travellife.po.BLIST;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.service.BlistService;
import com.travelsite.travellife.shiro.ShiroUtil;
import com.travelsite.travellife.shiro.SpringContextUtil;
import com.travelsite.travellife.utils.IpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.*;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static java.lang.Boolean.TRUE;

public class MyCsrfFilter extends OncePerRequestFilter {
    /**
      忽略GET, HEAD, TRACE, OPTIONS 请求
     */
    public static final RequestMatcher DEFAULT_CSRF_MATCHER = new MyCsrfFilter.DefaultRequiresCsrfMatcher();

    private static final String SHOULD_NOT_FILTER = "SHOULD_NOT_FILTER" + CsrfFilter.class.getName();

    private final Log logger = LogFactory.getLog(getClass());
    //生成csrf token
    private final CsrfTokenRepository tokenRepository;
    //负责拦截csrf的匹配
    private RequestMatcher requireCsrfProtectionMatcher = DEFAULT_CSRF_MATCHER;
    //拦截后拒绝方法
    private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

    public MyCsrfFilter(CsrfTokenRepository csrfTokenRepository) {
        Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
        this.tokenRepository = csrfTokenRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return TRUE.equals(request.getAttribute(SHOULD_NOT_FILTER));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        request.setAttribute(HttpServletResponse.class.getName(), response);
        //获取服务器保存token
        CsrfToken csrfToken = this.tokenRepository.loadToken(request);
        final boolean missingToken = csrfToken == null;
        //第一次访问未获得token则生成新token并保存
        if (missingToken) {
            csrfToken = this.tokenRepository.generateToken(request);
            this.tokenRepository.saveToken(csrfToken, request, response);
        }
        // 将token放入request中，_csrf
        request.setAttribute(CsrfToken.class.getName(), csrfToken);
        request.setAttribute(csrfToken.getParameterName(), csrfToken);
        //是否需要校验，除去POST
        if (!this.requireCsrfProtectionMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        //从客户端表单隐藏字段或请求头中获取token信息
        String actualToken = request.getHeader(csrfToken.getHeaderName());
        if (actualToken == null) {
            actualToken = request.getParameter(csrfToken.getParameterName());
        }
        //验证
        if (!csrfToken.getToken().equals(actualToken)) {
            String requestURI = request.getRequestURI();
            if (requestURI.contains("/system/")) {
                filterChain.doFilter(request, response);
                return;
            }
            //记录csrf 错误信息
            BlistService bean = SpringContextUtil.getBean(BlistService.class);
            String ip = IpUtils.getIPAddress(request);
            BLIST blist = bean.getBlistByIpAndType(ip,3);

            if (blist != null) {
                return;
            }else{
                blist = new BLIST();
                blist.setType(3);
                blist.setIpaddress(ip);
                blist.setTime(new Date());
            }

            bean.saveBlist(blist);

            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Invalid CSRF token found for "
                        + UrlUtils.buildFullRequestUrl(request));
            }
            if (missingToken) {
                this.accessDeniedHandler.handle(request, response,
                        new MissingCsrfTokenException(actualToken));
            }
            else {
                this.accessDeniedHandler.handle(request, response,
                        new InvalidCsrfTokenException(csrfToken, actualToken));
            }
            return;
        }

        filterChain.doFilter(request, response);
    }


    private static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
        private final HashSet<String> allowedMethods = new HashSet<>(
                Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

        @Override
        public boolean matches(HttpServletRequest request) {
            return !this.allowedMethods.contains(request.getMethod());
        }
    }
}
