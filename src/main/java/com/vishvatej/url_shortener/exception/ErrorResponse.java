package com.vishvatej.url_shortener.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime dateTime;
    private int status;
    private String error;
    private String message;
    private String path;
}
