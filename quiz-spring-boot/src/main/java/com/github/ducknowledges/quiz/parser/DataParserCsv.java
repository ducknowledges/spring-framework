package com.github.ducknowledges.quiz.parser;

import com.github.ducknowledges.quiz.domain.Record;
import com.github.ducknowledges.quiz.parser.exception.DataParserException;
import com.github.ducknowledges.quiz.parser.exception.DataParserFormatException;
import com.github.ducknowledges.quiz.parser.exception.DataParserReadException;
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

@Component
public class DataParserCsv implements DataParser {
    private final DataReader dataReader;
    private final RecordChecker recordChecker;
    private final CommunicationService communicationService;

    @Autowired
    public DataParserCsv(DataReader dataReader,
                         RecordChecker recordChecker,
                         CommunicationService communicationService) {
        this.dataReader = dataReader;
        this.recordChecker = recordChecker;
        this.communicationService = communicationService;
    }

    @Override
    public List<Record> parseToRecords() {
        try {
            List<Record> records = this.getRecords();
            return getCheckedRecords(records);
        } catch (DataParserException e) {
            communicationService.reportErrorToUser(e.getMessage());
        }
        return List.of();
    }

    private List<Record> getRecords() {
        Optional<Reader> readerOptional = dataReader.createReader();
        try (Reader csvReader = readerOptional.orElseThrow(IOException::new)) {
            return CSVFormat.RFC4180.parse(csvReader).getRecords()
                .stream()
                .map(csvRecord -> new Record(csvRecord.toList(), csvRecord.getRecordNumber()))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DataParserReadException(dataReader.getDataPath(), e);
        }
    }

    private List<Record> getCheckedRecords(List<Record> records) {
        List<Record> wrongRecords = recordChecker.filterWrongRecords(records);
        if (!wrongRecords.isEmpty()) {
            String description = getRecordNumbersDescription(wrongRecords);
            throw new DataParserFormatException(description);
        }
        return records;
    }

    private String getRecordNumbersDescription(List<Record> records) {
        return records
            .stream()
            .map(Record::getRecordNumber)
            .collect(Collectors.toList())
            .toString();
    }
}
