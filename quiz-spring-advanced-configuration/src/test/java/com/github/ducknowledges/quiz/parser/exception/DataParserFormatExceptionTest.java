package com.github.ducknowledges.quiz.parser.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataParserFormatExceptionTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        DataParserFormatException exception = new DataParserFormatException("description");
        String actual = "Parsed data has wrong formatted lines description";
        assertThat(exception.getMessage()).isEqualTo(actual);

    }
}