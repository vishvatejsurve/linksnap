package com.vishvatej.url_shortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private static final int MAX_REQUESTS=10;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    public boolean isAllowed(String clientId)
    {
        String key="ratelimit:" +clientId;
        Long count=redisTemplate.opsForValue().increment(key);

        if(count !=null && count ==1)
        {
            redisTemplate.expire(key,WINDOW);
        }

        return count !=null && count<=MAX_REQUESTS;
    }
}
