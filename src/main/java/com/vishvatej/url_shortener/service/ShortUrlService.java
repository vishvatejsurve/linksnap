package com.vishvatej.url_shortener.service;

public interface ShortUrlService {
    String createShortUrl(String longUrl);
    String getLongUrlAndTrackClick(String code);
}
