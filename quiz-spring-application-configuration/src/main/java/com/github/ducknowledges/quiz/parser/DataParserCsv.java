package com.github.ducknowledges.quiz.parser;

import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_DATA_ERROR;
import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_FORMAT_ERROR;

import com.github.ducknowledges.quiz.domain.Record;
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
        Optional<Reader> readerOptional = dataReader.createReader();
        try (Reader csvReader = readerOptional.orElseThrow(IOException::new)) {
            List<Record> records = readToRecords(csvReader);
            List<Record> wrongRecords = recordChecker.filterWrongRecords(records);
            if (wrongRecords.isEmpty()) {
                return records;
            }
            communicationService.reportErrorToUser(
                getParseFormatError(wrongRecords)
            );
        } catch (IOException e) {
            communicationService.reportErrorToUser(
                getParseDataError()
            );
        }
        return List.of();
    }

    private List<Record> readToRecords(Reader reader) throws IOException {
        return CSVFormat.RFC4180.parse(reader).getRecords()
            .stream()
            .map(csvRecord -> new Record(csvRecord.toList(), csvRecord.getRecordNumber()))
            .collect(Collectors.toList());
    }

    private String getParseDataError() {
        return PARSE_DATA_ERROR.message(dataReader.getDataPath());
    }

    private String getParseFormatError(List<Record> records) {
        List<Long> recordNumbers = getRecordNumbers(records);
        return PARSE_FORMAT_ERROR.message(dataReader.getDataPath(), recordNumbers.toString());
    }

    private List<Long> getRecordNumbers(List<Record> records) {
        return records
            .stream()
            .map(Record::getRecordNumber)
            .collect(Collectors.toList());
    }
}
