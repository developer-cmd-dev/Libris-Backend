package com.developerDev.Libris.ExceptionHandler;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime time;

    public ErrorResponse(String message , int status, LocalDateTime time) {
        this.message=message;
        this.status=status;
        this.time=time;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
