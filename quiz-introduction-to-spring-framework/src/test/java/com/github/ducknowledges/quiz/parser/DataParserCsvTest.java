package com.github.ducknowledges.quiz.parser;

import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_DATA_ERROR;
import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_FORMAT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.parser.formatchecker.ParsingFormatCheckerCsv;
import com.github.ducknowledges.quiz.reader.DataReaderCreator;
import com.github.ducknowledges.quiz.reader.ResourceDataReaderCreator;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
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
        String strings1 = "Is this a question?,yes";
        String strings2 = "Is this a question with options?,yes,yes,no";
        String parsedData = strings1 + System.lineSeparator() + strings2 + System.lineSeparator();

        List<String> record1 = List.of(strings1.split(","));
        List<String> record2 = List.of(strings2.split(","));
        List<List<String>> expected = List.of(record1, record2);

        DataReaderCreator dataReaderCreator = getDataReaderCreator("path", parsedData, false);
        ParsingFormatCheckerCsv parsingFormatCheckerCsv = getFormatChecker(false, "");
        DataParserCsv dataParserCsv = new DataParserCsv(dataReaderCreator, parsingFormatCheckerCsv);

        List<List<String>> records = dataParserCsv.parseToRecords();
        assertThat(records).hasSize(2).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("return empty list if csv file not available")
    void shouldReturnEmptyListWhenWrongResourcePath() {
        DataReaderCreator dataReaderCreator = getDataReaderCreator("path", "", true);
        ParsingFormatCheckerCsv parsingFormatCheckerCsv = getFormatChecker(false, "");
        DataParserCsv dataParserCsv = new DataParserCsv(dataReaderCreator, parsingFormatCheckerCsv);

        assertThat(dataParserCsv.parseToRecords()).isEmpty();
    }

    @Test
    @DisplayName("print error to console if csv file not available")
    void shouldPrintErrorToConsoleWhenWrongResourcePath() {
        String wrongPath = "wrongPath.csv";
        DataReaderCreator dataReaderCreator = getDataReaderCreator(wrongPath, "", true);
        ParsingFormatCheckerCsv parsingFormatCheckerCsv = getFormatChecker(false, "");

        new DataParserCsv(dataReaderCreator, parsingFormatCheckerCsv).parseToRecords();
        assertThat(err.toString())
            .isEqualTo(PARSE_DATA_ERROR.message(wrongPath) + System.lineSeparator());
    }

    @Test
    @DisplayName("return empty list if csv file has wrong format")
    void shouldReturnEmptyListIfWrongFormatCsv() {
        DataReaderCreator dataReaderCreator = getDataReaderCreator("", "", false);
        ParsingFormatCheckerCsv parsingFormatCheckerCsv = getFormatChecker(true, "");
        DataParserCsv dataParserCsv = new DataParserCsv(dataReaderCreator, parsingFormatCheckerCsv);

        List<List<String>> records = dataParserCsv.parseToRecords();
        assertThat(records).isEmpty();
    }

    @Test
    @DisplayName("print error to console if csv file has wrong format")
    void shouldPrintErrorToConsoleIfWrongFormatCsv() {
        String path = "wrongFormatTest.csv";
        String description = "wrong format at line: 2" + System.lineSeparator()
            + "wrong format at line: 4" + System.lineSeparator()
            + "wrong format at line: 6" + System.lineSeparator()
            + "wrong format at line: 7" + System.lineSeparator();
        DataReaderCreator dataReaderCreator = getDataReaderCreator(path, "", false);
        ParsingFormatCheckerCsv parsingFormatCheckerCsv = getFormatChecker(true, description);

        new DataParserCsv(dataReaderCreator, parsingFormatCheckerCsv).parseToRecords();
        assertThat(err.toString()).isEqualTo(
                PARSE_FORMAT_ERROR.message(path, description) + System.lineSeparator()
        );
    }

    private DataReaderCreator getDataReaderCreator(
        String dataPath,
        String readableData,
        boolean throwException
    ) {
        DataReaderCreator dataReaderCreator = mock(ResourceDataReaderCreator.class);
        byte[] bytes =  readableData.getBytes(StandardCharsets.UTF_8);
        InputStream stream = new ByteArrayInputStream(bytes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        try {
            if (throwException) {
                when(dataReaderCreator.createReader()).thenThrow(IOException.class);
            } else {
                when(dataReaderCreator.createReader()).thenReturn(bufferedReader);
            }
        } catch (IOException e) {
            fail("Test fail!!!");
        }
        when(dataReaderCreator.getDataPath()).thenReturn(dataPath);
        return dataReaderCreator;
    }

    private ParsingFormatCheckerCsv getFormatChecker(boolean hasWrongFormat, String description) {
        ParsingFormatCheckerCsv formatChecker = mock(ParsingFormatCheckerCsv.class);
        when(formatChecker.hasWrongFormattedRecords(any())).thenReturn(hasWrongFormat);
        when(formatChecker.getDescriptionOfWrongRecords(any())).thenReturn(description);
        return formatChecker;
    }
}