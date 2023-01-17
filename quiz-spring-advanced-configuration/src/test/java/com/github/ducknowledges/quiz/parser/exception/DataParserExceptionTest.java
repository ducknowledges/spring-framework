package com.github.ducknowledges.quiz.parser.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class DataParserReadException")
class DataParserExceptionTest {
    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        DataParserException exception = new DataParserException("message");
        String actual = "message";
        assertThat(exception.getMessage()).isEqualTo(actual);
    }

    @Test
    @DisplayName("correctly created by the constructor with cause")
    void shouldHaveCorrectConstructorWithCause() {
        Throwable cause = new Exception("message");
        DataParserException exception = new DataParserException("message", cause);
        String actual = "message";
        assertThat(exception.getMessage()).isEqualTo(actual);
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}