package com.github.ducknowledges.quiz.parser.formatchecker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class ParsingFormatCheckerCsv")
class ParsingFormatCheckerCsvTest {

    private static List<String> correctStrings;
    private static List<String> wrongStrings1;
    private static List<String> wrongStrings2;
    private static List<String> wrongStrings3;
    private static List<String> wrongStrings4;

    private static ParsingFormatChecker<CSVRecord> checkerCsv;


    @BeforeAll
    static void setUpAll() {
        correctStrings = List.of("Is this a question with options?", "yes", "yes", "no");
        wrongStrings1 = List.of("", "yes", "yes", "no");
        wrongStrings2 = List.of("Is this a question with options?", "", "yes", "no");
        wrongStrings3 = List.of("Is this a question?", "yes", "", "no");
        wrongStrings4 = List.of("Is this a question?");
        checkerCsv = new ParsingFormatCheckerCsv();
    }

    @Test
    @DisplayName("should return false if records are correct")
    void shouldReturnFalse() {
        CSVRecord record = getMockedRecord(correctStrings, 0);
        List<CSVRecord> records = List.of(record);
        assertThat(checkerCsv.hasWrongFormattedRecords(records)).isFalse();
    }

    @Test
    @DisplayName("should return true if at least one record has wrong format")
    void shouldReturnTrue() {
        CSVRecord record = getMockedRecord(wrongStrings1, 0);
        List<CSVRecord> records = List.of(record);
        assertThat(checkerCsv.hasWrongFormattedRecords(records)).isTrue();
    }

    @Test
    @DisplayName("should throw Exception with message if records has wrong format")
    void shouldReturnDescriptionOfWrongFormattedRecords() {
        List<CSVRecord> records = List.of(
                getMockedRecord(wrongStrings1, 0),
                getMockedRecord(wrongStrings2, 1),
                getMockedRecord(wrongStrings3, 2),
                getMockedRecord(wrongStrings4, 3)
        );
        String expected = "wrong format at line: 0" + System.lineSeparator()
            + "wrong format at line: 1" + System.lineSeparator()
            + "wrong format at line: 2" + System.lineSeparator()
            + "wrong format at line: 3" + System.lineSeparator();
        assertThat(checkerCsv.hasWrongFormattedRecords(records)).isTrue();
        assertThat(checkerCsv.getDescriptionOfWrongRecords(records)).isEqualTo(expected);
    }

    private CSVRecord getMockedRecord(List<String> strings, long recordNumber) {
        CSVRecord record = mock(CSVRecord.class);
        if (strings.size() < 2) {
            when(record.get(0)).thenReturn(strings.get(0));
        } else {
            when(record.get(0)).thenReturn(strings.get(0));
            when(record.get(1)).thenReturn(strings.get(1));
            when(record.get(2)).thenReturn(strings.get(2));
            when(record.get(3)).thenReturn(strings.get(3));
        }
        when(record.size()).thenReturn(strings.size());
        when(record.toList()).thenReturn(strings);
        when(record.getRecordNumber()).thenReturn(recordNumber);
        return record;
    }
}