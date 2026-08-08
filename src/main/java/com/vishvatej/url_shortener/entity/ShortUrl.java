package com.vishvatej.url_shortener.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_url")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 2048)
    private String longUrl;

    @Column(unique = true,length = 10)
    private String shortCode;

    private LocalDateTime createdAt;

    private long clickCount=0;
}
