package com.satvik.redis_rate_limiter.interceptor;

import com.satvik.redis_rate_limiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    public RateLimiterInterceptor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // Use client IP as identifier (later we can switch to JWT userId)
        String clientId = request.getRemoteAddr();
        String key = "rate:" + clientId;

        if (!rateLimiterService.allowRequest(key)) {
            response.setStatus(429);
            response.getWriter().write("Too Many Requests");
            return false;
        }

        return true;
    }
}
