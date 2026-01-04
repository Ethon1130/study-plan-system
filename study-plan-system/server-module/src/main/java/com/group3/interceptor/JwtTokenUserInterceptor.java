package com.group3.interceptor;


import com.group3.common.context.BaseContext;
import com.group3.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JwtTokenUserInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 支持两种token格式：标准的Authorization Bearer 和自定义的token header
        String token = request.getHeader("token");
        String authHeader = request.getHeader("Authorization");
        
        // 如果没有token header，尝试从Authorization header中提取
        if ((token == null || token.trim().isEmpty()) && authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // 移除"Bearer "前缀
            log.info("从Authorization header中提取token: Bearer {}", token.substring(0, Math.min(20, token.length())) + "...");
        }
        
        if (token == null || token.trim().isEmpty()) {
            log.warn("请求头中未携带token，请求路径：{}, Authorization: {}", request.getRequestURI(), authHeader);
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"未登录或token已过期，请先登录\",\"data\":null}");
            return false;
        }

        try {
            log.info("用户jwt校验:{}", token);
            Claims claims = JwtUtils.parseToken(token);
            Long userId = Long.valueOf(claims.get("userId").toString());
            log.info("当前用户id：{}", userId);
            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception ex) {
            log.error("用户token校验失败", ex);
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"token无效或已过期\",\"data\":null}");
            return false;
        }
    }
}