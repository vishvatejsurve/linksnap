package com.vishvatej.url_shortener.controller;

import com.vishvatej.url_shortener.entity.ShortUrl;
import com.vishvatej.url_shortener.exception.ResourceNotFoundException;
import com.vishvatej.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ShortUrlRepository repository;

    @GetMapping("/{code}")
    public ShortUrl getStats(@PathVariable String code)
    {
        return repository.findByShortCode(code).orElseThrow(()->new ResourceNotFoundException("Short Code not Found"));
    }
}
