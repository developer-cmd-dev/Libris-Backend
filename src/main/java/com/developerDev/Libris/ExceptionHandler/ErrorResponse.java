package com.developerDev.Libris.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime time;

}
