package com.github.ducknowledges.bookstore.dto.error;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ValidationErrorDto {

    private final String status;

    private final Map<String, String> fieldErrors;

    public ValidationErrorDto() {
        status = HttpStatus.BAD_REQUEST.name();
        fieldErrors = new HashMap<>();
    }

    public String getStatus() {
        return status;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldError(String fieldName, String message) {
        fieldErrors.put(fieldName, message);
    }
}
