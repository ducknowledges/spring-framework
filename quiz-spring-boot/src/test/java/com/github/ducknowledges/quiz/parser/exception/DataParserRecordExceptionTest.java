package com.github.ducknowledges.quiz.parser.exception;

import static com.github.ducknowledges.quiz.parser.exception.DataParserRecordException.ParserError.RECORD_ERROR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataParserRecordExceptionTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        DataParserRecordException exception = new DataParserRecordException(
            RECORD_ERROR, "description");
        assertThat(exception.getMessage()).isEqualTo(RECORD_ERROR.message() + " description");

    }

    @Test
    @DisplayName("should has correct record error")
    void shouldHasAccessError() {
        assertThat(RECORD_ERROR.name()).isEqualTo("RECORD_ERROR");
    }

    @Test
    @DisplayName("should has correct record error message")
    void shouldHasRecordErrorMessage() {
        assertThat(RECORD_ERROR.message()).isEqualTo("parsed data has wrong format lines");
    }

}