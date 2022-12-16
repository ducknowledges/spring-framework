package com.github.ducknowledges.quiz.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class DataReaderResource")
class DataReaderResourceTest {

    private DataReaderResource dataReaderCreator;

    @BeforeEach
    void setUp() {
        dataReaderCreator  = new DataReaderResource("quizTest.csv");
    }

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        String expected = "quizTest.csv";
        assertThat(dataReaderCreator.getDataPath()).isEqualTo(expected);
    }

    @Test
    @DisplayName("correctly create resource buffered reader")
    void shouldReturnBufferedReaderForResource() throws IOException {
        String expected = "Is this a question?,yes" + System.lineSeparator()
                + "Is this a question with options?,yes,yes,no";
        Optional<Reader> readerOptional = dataReaderCreator.createReader();
        try (Reader reader = readerOptional.get()) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String actual = bufferedReader.lines().collect(
                Collectors.joining(System.lineSeparator())
            );
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("should throw IllegalArgumentException with message")
    void shouldThrowException() {
        String wrongPath = "file.csv";
        DataReader dataReader = new DataReaderResource(wrongPath);
        Optional<Reader> reader = dataReader.createReader();
        assertThat(reader.isEmpty()).isTrue();
    }
}