package com.github.ducknowledges.quiz.parser.exception;

import static com.github.ducknowledges.quiz.parser.exception.DataParserException.ParserError.PARSE_ERROR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class DataParserException")
class DataParserExceptionTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        DataParserException exception = new DataParserException(PARSE_ERROR, "description");
        assertThat(exception.getMessage()).isEqualTo(
            PARSE_ERROR.message() + " description");

    }


    @Test
    @DisplayName("should has correct parse error")
    void shouldHasParseError() {
        assertThat(PARSE_ERROR.name()).isEqualTo("PARSE_ERROR");
    }

    @Test
    @DisplayName("should has correct wrong line format code message")
    void shouldHasParseErrorMessage() {
        assertThat(PARSE_ERROR.message()).isEqualTo("can't parse data");
    }
}