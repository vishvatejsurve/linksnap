package com.vishvatej.url_shortener.service.impl;

import com.vishvatej.url_shortener.entity.ShortUrl;
import com.vishvatej.url_shortener.exception.ResourceNotFoundException;
import com.vishvatej.url_shortener.repository.ShortUrlRepository;
import com.vishvatej.url_shortener.service.ShortUrlService;
import com.vishvatej.url_shortener.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository repository;
    private final StringRedisTemplate redisTemplate;
    private final ClickCountAsyncService clickCountAsyncService;


    private static final String CACHE_PREFIX="shorturl:";
    private static final Duration CACHE_TTL=Duration.ofHours(24);

    @Override
    @Transactional
    public String createShortUrl(String longUrl) {

        ShortUrl entity=new ShortUrl();
        entity.setLongUrl(longUrl);
        entity.setCreatedAt(LocalDateTime.now());

       entity= repository.save(entity);

       String code= Base62Encoder.encode(entity.getId());
       entity.setShortCode(code);
       repository.save(entity);

       redisTemplate.opsForValue().set(CACHE_PREFIX+code, longUrl,CACHE_TTL);

       return code;
    }

    @Override
    public String getLongUrlAndTrackClick(String code) {

        String cacheKey=CACHE_PREFIX+code;
        String longUrl = redisTemplate.opsForValue().get(cacheKey);

        if (longUrl !=null)
        {
            log.info("Cache HIT for code:{}",code);
        }else{
            log.info("Cache MISS for code:{} - falling back to MYSql",code);
            ShortUrl entity= repository.findByShortCode(code).orElseThrow(()-> new ResourceNotFoundException("Short link not found"));

            longUrl =entity.getLongUrl();

            redisTemplate.opsForValue().set(cacheKey,longUrl,CACHE_TTL);
        }
        clickCountAsyncService.incrementClickCountAsync(code);
       return longUrl;
    }


}
