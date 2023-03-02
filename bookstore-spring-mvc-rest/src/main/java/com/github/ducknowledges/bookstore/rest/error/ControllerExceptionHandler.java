package com.github.ducknowledges.bookstore.rest.error;

import com.github.ducknowledges.bookstore.dto.error.ErrorDto;
import com.github.ducknowledges.bookstore.dto.error.ValidationErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto handleNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorDto errorDto = new ValidationErrorDto();
        BindingResult result = e.getBindingResult();
        for (FieldError fieldError : result.getFieldErrors()) {
            errorDto.setFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorDto;
    }

    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleNotFountException(ElementNotFoundException e) {
        String errorMessage = String.format(
            "%s with id = %d was not found", e.getDomainName(), e.getId()
        );
        return new ErrorDto(HttpStatus.NOT_FOUND, errorMessage);
    }

}
