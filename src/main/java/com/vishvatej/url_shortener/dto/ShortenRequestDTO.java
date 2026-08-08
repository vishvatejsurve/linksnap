package com.vishvatej.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ShortenRequestDTO(@NotBlank @URL
                                String longUrl) {}
