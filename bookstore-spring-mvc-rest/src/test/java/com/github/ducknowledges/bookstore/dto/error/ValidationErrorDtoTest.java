package com.github.ducknowledges.bookstore.dto.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.ducknowledges.bookstore.dto.BookDto;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Class ValidationErrorDto")
class ValidationErrorDtoTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHasCorrectConstructorWithoutId() {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        validationErrorDto.setFieldError("field", "message");
        assertAll(
            () -> assertThat(validationErrorDto.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.name()),
            () -> assertThat(validationErrorDto.getFieldErrors())
                .containsEntry("field", "message")
        );
    }

}