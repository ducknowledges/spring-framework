package com.github.ducknowledges.quiz.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.Record;
import com.github.ducknowledges.quiz.parser.recordchecker.RecordChecker;
import com.github.ducknowledges.quiz.parser.recordchecker.RecordCheckerImpl;
import com.github.ducknowledges.quiz.reader.DataReader;
import com.github.ducknowledges.quiz.reader.DataReaderResource;
import com.github.ducknowledges.quiz.service.CommunicationService;
import com.github.ducknowledges.quiz.service.CommunicationServiceImpl;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Class ParserCsv")
class DataParserCsvTest {

    private Reader reader;
    private List<Record> records;
    private List<Record> wrongRecords;
    private CommunicationService communicationService;
    private DataReader dataReader;
    private RecordChecker recordChecker;


    @BeforeEach
    public void setup() {
        String string1 = "Is this a question?,yes";
        String string2 = "Is this a question with options?,yes,yes,no";
        String data = string1 + System.lineSeparator() + string2 + System.lineSeparator();

        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        reader = new BufferedReader(new InputStreamReader(stream));

        String wrongString1 = ",yes,yes,no";
        String wrongString2 = "Is this a question with options?,,yes,no";

        records = List.of(
            getRecord(string1, 1),
            getRecord(string2, 2)
        );
        wrongRecords = List.of(
            getRecord(wrongString1, 3),
            getRecord(wrongString2, 4)
        );

        communicationService = mock(CommunicationServiceImpl.class);
        dataReader = mock(DataReaderResource.class);
        recordChecker = mock(RecordCheckerImpl.class);
    }

    private Record getRecord(String string, int recordNumber) {
        return new Record(List.of(string.split(",")), recordNumber);
    }

    @Test
    @DisplayName("correctly parse csv data to list of records")
    void shouldParseCsvToRecords() {
        when(dataReader.createReader()).thenReturn(Optional.of(reader));
        when(recordChecker.filterWrongRecords(any())).thenReturn(List.of());

        DataParserCsv dataParserCsv = new DataParserCsv(
            dataReader, recordChecker, communicationService
        );

        List<Record> actual = dataParserCsv.parseToRecords();
        List<Record> expected = records;
        assertThat(actual).hasSize(2).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("return empty list if csv file has wrong records")
    void shouldReturnEmptyListWhenWrongRecordCsv() {
        when(dataReader.createReader()).thenReturn(Optional.of(reader));
        when(recordChecker.filterWrongRecords(any())).thenReturn(wrongRecords);
        DataParserCsv dataParserCsv = new DataParserCsv(
            dataReader, recordChecker, communicationService
        );
        List<Record> actual = dataParserCsv.parseToRecords();
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("report error to user if csv file has wrong format")
    void shouldReportErrorToUserWhenWrongRecordCsv() {
        when(dataReader.getDataPath()).thenReturn("path");
        when(dataReader.createReader()).thenReturn(Optional.of(reader));
        when(recordChecker.filterWrongRecords(any())).thenReturn(wrongRecords);
        DataParserCsv dataParserCsv = new DataParserCsv(
            dataReader, recordChecker, communicationService
        );
        dataParserCsv.parseToRecords();
        String expected = "path: has wrong format" + System.lineSeparator()
            + List.of(wrongRecords.get(0).getRecordNumber(), wrongRecords.get(1).getRecordNumber());
        verify(communicationService, times(1)).reportErrorToUser(expected);
    }

    @Test
    @DisplayName("return empty list if csv file not available")
    void shouldReturnEmptyListWhenWrongResourcePath() {
        when(dataReader.createReader()).thenReturn(Optional.empty());
        DataParserCsv dataParserCsv = new DataParserCsv(
            dataReader, recordChecker, communicationService
        );
        assertThat(dataParserCsv.parseToRecords()).isEmpty();
    }

    @Test
    @DisplayName("report error to user if csv file not available")
    void shouldReportErrorToUserWhenWrongResourcePath() {
        when(dataReader.getDataPath()).thenReturn("path");
        when(dataReader.createReader()).thenReturn(Optional.empty());
        DataParserCsv dataParserCsv = new DataParserCsv(
            dataReader, recordChecker, communicationService
        );
        dataParserCsv.parseToRecords();
        String expected = "path: can't parse data";
        verify(communicationService, times(1)).reportErrorToUser(expected);
    }

}