package com.example.server.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nonlinearthink
 */
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setHeader("Access-Control-Allow-Origin","*");
        res.setHeader("Access-Control-Allow-Credentials","true");
        res.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Max-Age","86400");
        res.setHeader("Access-Control-Allow-Headers","*");
        if(req.getMethod().equals("OPTIONS")){
            res.setStatus(200);
            return;
        }
        filterChain.doFilter(servletRequest, res);
    }

}
