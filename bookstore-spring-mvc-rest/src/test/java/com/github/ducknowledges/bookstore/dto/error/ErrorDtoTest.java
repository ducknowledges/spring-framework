package com.github.ducknowledges.bookstore.dto.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Class ErrorDto")
class ErrorDtoTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHasCorrectConstructorWithoutId() {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, "message");
        assertAll(
            () -> assertThat(errorDto.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.name()),
            () -> assertThat(errorDto.getMessage()).isEqualTo("message")
        );
    }

}