package com.vishvatej.url_shortener.controller;

import com.vishvatej.url_shortener.dto.ShortenRequestDTO;
import com.vishvatej.url_shortener.dto.ShortenResponseDTO;
import com.vishvatej.url_shortener.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class UrlController {

    private final ShortUrlService service;

    @Value("${app.base-url}")
    private String baseUrl;

    @PostMapping("api/shorten")
    public ResponseEntity<ShortenResponseDTO> createShortUrl(@Valid @RequestBody ShortenRequestDTO request)
    {
        String code=service.createShortUrl(request.longUrl());
        return ResponseEntity.ok(new ShortenResponseDTO(code,baseUrl + "/"+ code));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String longUrl = service.getLongUrlAndTrackClick(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }

}
