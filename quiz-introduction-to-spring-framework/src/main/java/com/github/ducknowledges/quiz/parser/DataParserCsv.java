package com.github.ducknowledges.quiz.parser;

import com.github.ducknowledges.quiz.domain.Record;
import com.github.ducknowledges.quiz.parser.exception.DataParserException;
import com.github.ducknowledges.quiz.parser.exception.DataParserException.ParserError;
import com.github.ducknowledges.quiz.parser.recordchecker.RecordChecker;
import com.github.ducknowledges.quiz.reader.DataReader;
import com.github.ducknowledges.quiz.service.CommunicationService;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class DataParserCsv implements DataParser {
    private final DataReader dataReader;
    private final RecordChecker recordChecker;
    private final CommunicationService communicationService;

    public DataParserCsv(DataReader dataReader,
                         RecordChecker recordChecker,
                         CommunicationService communicationService) {
        this.dataReader = dataReader;
        this.recordChecker = recordChecker;
        this.communicationService = communicationService;
    }

    @Override
    public List<Record> parseToRecords() {
        Optional<Reader> readerOptional = dataReader.createReader();
        String error;
        try (Reader csvReader = readerOptional.orElseThrow(
            () -> new DataParserException(ParserError.ACCESS_ERROR))) {
            List<Record> records = readToRecords(csvReader);
            return getCheckedRecords(records);
        } catch (DataParserException e) {
            error = e.getMessage(dataReader.getDataPath());
        } catch (IOException e) {
            error = ParserError.ACCESS_ERROR.message(dataReader.getDataPath()) + e.getMessage();
        }
        communicationService.reportErrorToUser(error);
        return List.of();
    }

    private List<Record> readToRecords(Reader reader) throws DataParserException {
        try {
            return CSVFormat.RFC4180.parse(reader).getRecords()
                .stream()
                .map(csvRecord -> new Record(csvRecord.toList(), csvRecord.getRecordNumber()))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DataParserException(ParserError.PARSE_ERROR, e.getMessage());
        }
    }

    private List<Record> getCheckedRecords(List<Record> records) throws DataParserException {
        List<Record> wrongRecords = recordChecker.filterWrongRecords(records);
        if (!wrongRecords.isEmpty()) {
            String description = getRecordNumbers(wrongRecords).toString();
            throw new DataParserException(ParserError.RECORD_ERROR, description);
        }
        return records;
    }

    private List<Long> getRecordNumbers(List<Record> records) {
        return records
            .stream()
            .map(Record::getRecordNumber)
            .collect(Collectors.toList());
    }
}
