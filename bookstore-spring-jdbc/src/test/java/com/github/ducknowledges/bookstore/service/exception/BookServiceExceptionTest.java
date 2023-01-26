package com.github.ducknowledges.bookstore.service.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class BookServiceExceptionTest")
class BookServiceExceptionTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        BookServiceException exception = new BookServiceException("message");
        String expected = "message";
        String actual = exception.getMessage();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("correctly created by the constructor with cause")
    void shouldHaveCorrectConstructorWithCause() {
        Throwable expectedCause = new Exception("message");
        BookServiceException exception = new BookServiceException("message", expectedCause);

        Throwable actualCause = exception.getCause();
        assertThat(actualCause).isEqualTo(expectedCause);

        String expectedMessage = "message";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}