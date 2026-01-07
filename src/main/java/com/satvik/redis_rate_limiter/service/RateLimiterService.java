package com.satvik.redis_rate_limiter.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SECONDS = 60;

    private final StringRedisTemplate redisTemplate;

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean allowRequest(String key) {

        Long current = redisTemplate.opsForValue().increment(key, 0);

        if (current == null || current == 0) {
            redisTemplate.opsForValue().increment(key);
            redisTemplate.expire(key, WINDOW_SECONDS, TimeUnit.SECONDS);
            return true;
        }

        if (current < MAX_REQUESTS) {
            redisTemplate.opsForValue().increment(key);
            return true;
        }

        return false;
    }
}
