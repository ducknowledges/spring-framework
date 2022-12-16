package com.github.ducknowledges.quiz.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Enum DataParserError")
class DataParserErrorTest {

    @Test
    @DisplayName("should get error values")
    void shouldReturnErrorValues() {
        DataParserError[] dataReaderErrors = {
            DataParserError.PARSE_DATA_ERROR,
            DataParserError.PARSE_FORMAT_ERROR
        };
        assertThat(DataParserError.values())
                .isEqualTo(dataReaderErrors);
    }

    @Test
    @DisplayName("get parse data error message")
    void shouldReturnMessageOfParseDataError() {
        String path = "file";
        assertThat(DataParserError.PARSE_DATA_ERROR.message(path))
                .isEqualTo(path + ": can't parse data");
    }

    @Test
    @DisplayName("get parse format error message")
    void shouldReturnMessageOfWrongFormatError() {
        String path = "file";
        assertThat(DataParserError.PARSE_FORMAT_ERROR.message(path))
                .isEqualTo(path + ": has wrong format");
    }
}