package com.vishvatej.url_shortener.service.impl;

import com.vishvatej.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ClickCountAsyncService {

    private final ShortUrlRepository repository;

    @Async("taskExecutor")
    @Transactional
    public void incrementClickCountAsync(String code)
    {
        repository.incrementClickCount(code);
    }
}
