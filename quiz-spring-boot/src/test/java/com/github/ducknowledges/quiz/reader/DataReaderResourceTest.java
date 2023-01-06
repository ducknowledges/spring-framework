package com.github.ducknowledges.quiz.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.config.AppProps;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class DataReaderResource")
class DataReaderResourceTest {

    private AppProps props;
    private DataReaderResource dataReaderCreator;

    @BeforeEach
    void setUp() {
        props = mock(AppProps.class);
        when(props.getPath()).thenReturn("quiz_en-test.csv");
        dataReaderCreator  = new DataReaderResource(props);
    }

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        String expected = "quiz_en-test.csv";
        assertThat(dataReaderCreator.getDataPath()).isEqualTo(expected);
    }

    @Test
    @DisplayName("correctly create resource buffered reader")
    void shouldReturnBufferedReaderForResource() throws IOException {
        String expected = "Is this a question?,yes" + System.lineSeparator()
                + "Is this a question with options?,yes,yes,no";
        Optional<Reader> readerOptional = dataReaderCreator.createReader();
        try (Reader reader = readerOptional.orElseThrow()) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String actual = bufferedReader.lines().collect(
                Collectors.joining(System.lineSeparator())
            );
            assertThat(actual).isEqualTo(expected);
        } catch (NoSuchElementException e) {
            fail("Test fails");
        }
    }

    @Test
    @DisplayName("should return empty optional of data reader")
    void shouldReturnEmptyOptionalOfDataReader() {
        when(props.getPath()).thenReturn("file.csv");
        DataReader dataReader = new DataReaderResource(props);
        Optional<Reader> reader = dataReader.createReader();
        assertThat(reader.isEmpty()).isTrue();
    }
}