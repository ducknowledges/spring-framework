package com.github.ducknowledges.quiz.parser;

import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_DATA_ERROR;
import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_FORMAT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.quiz.parser.formatchecker.ParsingFormatCheckerCsv;
import com.github.ducknowledges.quiz.reader.DataReaderCreator;
import com.github.ducknowledges.quiz.reader.DataReaderCreatorResource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class ParserCsv")
class DataParserCsvTest {

    private ByteArrayOutputStream err;

    @BeforeEach
    public void setup() {
        err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));
    }

    @Test
    @DisplayName("correctly parse csv data to list of records")
    void shouldParseCsvToQuizzes() {
        List<String> strings1 = List.of("Is this a question?", "yes");
        List<String> strings2 = List.of("Is this a question with options?", "yes", "yes", "no");
        List<List<String>> expected = List.of(strings1, strings2);

        List<List<String>> records = getDataParserCsvFor("quizTest.csv").parseToRecords();
        assertThat(records).hasSize(2).hasSameElementsAs(expected);
    }

    private DataParserCsv getDataParserCsvFor(String path) {
        DataReaderCreator dataReaderCreator = new DataReaderCreatorResource(path);
        ParsingFormatCheckerCsv formatCheckerCsv = new ParsingFormatCheckerCsv();
        return  new DataParserCsv(dataReaderCreator, formatCheckerCsv);
    }

    @Test
    @DisplayName("return empty list if csv file not available")
    void shouldReturnEmptyListWhenWrongResourcePath() {
        List<List<String>> records = getDataParserCsvFor("wrongPath.csv").parseToRecords();
        assertThat(records).isEmpty();
    }

    @Test
    @DisplayName("return empty list if csv file has wrong format")
    void shouldReturnEmptyListIfWrongFormat() {
        List<List<String>> records = getDataParserCsvFor("wrongFormatTest.csv").parseToRecords();
        assertThat(records).isEmpty();
    }



    @Test
    @DisplayName("print error to console if csv file not available")
    void shouldPrintErrorToConsoleWhenWrongResourcePath() {
        String wrongPath = "wrongPath.csv";
        getDataParserCsvFor(wrongPath).parseToRecords();
        assertThat(err.toString())
                .isEqualTo(PARSE_DATA_ERROR.message(wrongPath) + System.lineSeparator());
    }

    @Test
    @DisplayName("print error to console if csv file has wrong format")
    void shouldPrintErrorToConsoleWhenQuizCsvHasOnlyQuestion() {
        String path = "wrongFormatTest.csv";
        getDataParserCsvFor(path).parseToRecords();
        String description = "wrong format at line: 2" + System.lineSeparator()
                + "wrong format at line: 4" + System.lineSeparator()
                + "wrong format at line: 6" + System.lineSeparator()
                + "wrong format at line: 7" + System.lineSeparator();
        assertThat(err.toString()).isEqualTo(
                PARSE_FORMAT_ERROR.message(path, description) + System.lineSeparator()
        );
    }
}