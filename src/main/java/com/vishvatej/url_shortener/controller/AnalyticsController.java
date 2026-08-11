package com.vishvatej.url_shortener.controller;

import com.vishvatej.url_shortener.entity.ShortUrl;
import com.vishvatej.url_shortener.exception.ResourceNotFoundException;
import com.vishvatej.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ShortUrlRepository repository;

    @GetMapping("/{code}")
    public ShortUrl getStats(@PathVariable String code)
    {
        return repository.findByShortCode(code).orElseThrow(()->new ResourceNotFoundException("Short Code not Found"));
    }
}
