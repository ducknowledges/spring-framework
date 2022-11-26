package com.github.ducknowledges.quiz.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("correctly created by the constructor")
class ParserErrorMessageTest {

    @Test
    @DisplayName("get error values")
    void shouldReturnValues() {
        ParserErrorMessage[] parserErrorMessages = {
            ParserErrorMessage.WRONG_FORMAT,
            ParserErrorMessage.RESOURCE_ERROR
        };
        assertThat(ParserErrorMessage.values())
                .isEqualTo(parserErrorMessages);
    }

    @Test
    @DisplayName("get wrong format at line error message")
    void shouldReturnMessageOfEmptyQuestion() {
        int line = 2;
        assertThat(ParserErrorMessage.WRONG_FORMAT.message(line))
                .isEqualTo("wrong format at line 2");
    }

    @Test
    @DisplayName("get resource error message")
    void shouldReturnMessageOfResourceError() {
        assertThat(ParserErrorMessage.RESOURCE_ERROR.message())
                .isEqualTo("resource not available");
    }
}