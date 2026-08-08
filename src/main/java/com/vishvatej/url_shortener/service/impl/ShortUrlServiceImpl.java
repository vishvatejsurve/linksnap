package com.vishvatej.url_shortener.service.impl;

import com.vishvatej.url_shortener.entity.ShortUrl;
import com.vishvatej.url_shortener.exception.ResourceNotFoundException;
import com.vishvatej.url_shortener.repository.ShortUrlRepository;
import com.vishvatej.url_shortener.service.ShortUrlService;
import com.vishvatej.url_shortener.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository repository;

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

       return code;
    }

    @Override
    public String getLongUrlAndTrackClick(String code) {
       ShortUrl entity= repository.findByShortCode(code).orElseThrow(()-> new ResourceNotFoundException("Short link not found"));

       entity.setClickCount(entity.getClickCount()+1);
       repository.save(entity);

       return entity.getLongUrl();
    }
}
