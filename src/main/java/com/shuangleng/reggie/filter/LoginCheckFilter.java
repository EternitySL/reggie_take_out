package com.shuangleng.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.shuangleng.reggie.common.BaseContext;
import com.shuangleng.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/5/25 16:21
 * @description：filter拦截器
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();//匹配是否相等

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //分别转型获取到子类的方法
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求的URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求{}",requestURI);
        //需要放行的静态资源
        //返回的URI是/XXXX/**
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        if (check(requestURI, urls)) {
            log.info("本次请求不需要处理{}",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employee") != null){
            log.info("用户已经登录id为{}",request.getSession().getAttribute("employee"));
            long id = Thread.currentThread().getId();
            log.info("生成的id={}",id);
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }
    public boolean check(String requestURI, String[] urls) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return match;
            }
        }return false;
    }
}
