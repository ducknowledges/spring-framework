package com.github.ducknowledges.quiz.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class ResourceDataReaderCreator")
class ResourceDataReaderCreatorTest {

    private ResourceDataReaderCreator dataReaderCreator;

    @BeforeEach
    void setUp() {
        dataReaderCreator  = new ResourceDataReaderCreator("quizTest.csv");
    }

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        String path = "quizTest.csv";
        assertThat(dataReaderCreator.getDataPath()).isEqualTo(path);
    }

    @Test
    @DisplayName("correctly create resource buffered reader")
    void shouldReturnBufferedReaderForResource() throws IOException {
        String expected = "Is this a question?,yes" + System.lineSeparator()
                + "Is this a question with options?,yes,yes,no";
        try (BufferedReader reader = dataReaderCreator.createReader()) {
            String actual = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("should throw IOException with message")
    void shouldThrowException() {
        String wrongPath = "file.csv";
        DataReaderCreator dataReaderCreator  = new ResourceDataReaderCreator(wrongPath);
        assertThatThrownBy(dataReaderCreator::createReader)
                .isInstanceOf(IOException.class)
                .hasMessage("input stream is null");
    }
}