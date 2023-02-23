package com.github.ducknowledges.bookstore.dto.error;

import org.springframework.http.HttpStatus;

public class ErrorDto {
    private final String status;
    private final String message;

    public ErrorDto(HttpStatus status, String message) {
        this.status = status.name();
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
