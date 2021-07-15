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
import java.util.Objects;

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
        log.info("携带的token: " + token);
        if (token != null) {
            Map<String, String> data = JwtUtil.verifyToken(token);
            if (data.get("user_id") != null && Objects.equals(authRedisTemplate.opsForValue().get("wechat@" + data.get("user_id")), token)) {
                log.info("token解析结果: user_id " + data.get("user_id") + " session_key " + data.get("session_key"));
                req.setAttribute("user_id", data.get("user_id"));
                req.setAttribute("session_key", data.get("session_key"));
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else if (data.get("admin_id") != null &&
                    Objects.equals(authRedisTemplate.opsForValue().get("admin@" + data.get("admin_id")), token)) {
                log.info("token解析结果: admin_id " + data.get("admin_id"));
                req.setAttribute("admin_id", data.get("admin_id"));
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
