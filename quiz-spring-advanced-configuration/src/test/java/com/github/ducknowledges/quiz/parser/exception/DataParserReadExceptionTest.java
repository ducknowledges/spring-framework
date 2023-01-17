package com.github.ducknowledges.quiz.parser.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class DataParserReadException")
class DataParserReadExceptionTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        DataParserReadException exception = new DataParserReadException("path");
        String actual = "Can't read data path";
        assertThat(exception.getMessage()).isEqualTo(actual);
    }

    @Test
    @DisplayName("correctly created by the constructor with cause")
    void shouldHaveCorrectConstructorWithCause() {
        Throwable cause = new Exception("message");
        DataParserReadException exception = new DataParserReadException("path", cause);
        String actual = "Can't read data path";
        assertThat(exception.getMessage()).isEqualTo(actual);
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}