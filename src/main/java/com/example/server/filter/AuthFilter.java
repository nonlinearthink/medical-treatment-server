package com.example.server.filter;

import com.example.server.util.JwtUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author nonlinearthink
 */
@Slf4j
public class AuthFilter implements Filter {

    @Resource(name = "authRedisTemplate")
    private StringRedisTemplate authRedisTemplate;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String token = req.getHeader("token");
        if (token != null) {
            String userId = authRedisTemplate.opsForValue().get(token);
            Map<String, String> data = JwtUtil.verifyToken(token);
            if (userId != null && userId.equals(data.get("user_id"))) {
                req.setAttribute("user_id", userId);
                req.setAttribute("session_key", data.get("session_key"));
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        res.setStatus(401);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
