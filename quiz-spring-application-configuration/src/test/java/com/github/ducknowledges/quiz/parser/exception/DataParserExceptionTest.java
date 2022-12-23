package com.github.ducknowledges.quiz.parser.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.quiz.parser.exception.DataParserException.ParserError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class DataParserException")
class DataParserExceptionTest {

    private final String prefix = "prefix";

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        DataParserException exception = new DataParserException(ParserError.RECORD_ERROR);
        assertThat(exception.getMessage()).isEqualTo(
            ParserError.RECORD_ERROR.message() + System.lineSeparator());

    }

    @Test
    @DisplayName("correctly created by the constructor with two arguments")
    void shouldHaveCorrectConstructorWithTwoArg() {
        DataParserException exception = new DataParserException(
            ParserError.PARSE_ERROR, "description"
        );
        assertThat(exception.getMessage()).isEqualTo(
            ParserError.PARSE_ERROR.message()
                + System.lineSeparator()
                + "description");
    }

    @Test
    @DisplayName("return exception message with prefix")
    void shouldReturnMessageWithPrefix() {
        DataParserException exception = new DataParserException(ParserError.RECORD_ERROR);
        assertThat(exception.getMessage(prefix)).isEqualTo(
            ParserError.RECORD_ERROR.message(prefix) + System.lineSeparator());
    }

    @Test
    @DisplayName("should has correct access error")
    void shouldHasAccessError() {
        assertThat(ParserError.ACCESS_ERROR.name()).isEqualTo("ACCESS_ERROR");
    }

    @Test
    @DisplayName("should has correct access error message")
    void shouldHasAccessErrorMessage() {
        assertThat(ParserError.ACCESS_ERROR.message()).isEqualTo("parsed data not available");
        assertThat(ParserError.ACCESS_ERROR.message(prefix))
            .isEqualTo(prefix + " parsed data not available");
    }

    @Test
    @DisplayName("should has correct record error")
    void shouldHasRecordError() {
        assertThat(ParserError.RECORD_ERROR.name()).isEqualTo("RECORD_ERROR");
    }

    @Test
    @DisplayName("should has correct access error message")
    void shouldHasRecordErrorMessage() {
        assertThat(ParserError.RECORD_ERROR.message())
            .isEqualTo("parsed data has wrong format lines");
        assertThat(ParserError.RECORD_ERROR.message(prefix))
            .isEqualTo(prefix + " parsed data has wrong format lines");
    }

    @Test
    @DisplayName("should has correct parse error")
    void shouldHasParseError() {
        assertThat(ParserError.PARSE_ERROR.name()).isEqualTo("PARSE_ERROR");
    }

    @Test
    @DisplayName("should has correct wrong line format code message")
    void shouldHasParseErrorMessage() {
        assertThat(ParserError.PARSE_ERROR.message()).isEqualTo("can't parse data");
        assertThat(ParserError.PARSE_ERROR.message(prefix))
            .isEqualTo(prefix + " can't parse data");
    }
}