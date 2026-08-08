package com.vishvatej.url_shortener.repository;

import com.vishvatej.url_shortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {
   Optional<ShortUrl> findByShortCode(String shortCode);
}
